package calamadevs.service;

import calamadevs.domain.Maintenance.MaintenanceItem;
import calamadevs.domain.Maintenance.MaintenanceRecord;
import calamadevs.domain.Maintenance.MaintenanceRecordItem;
import calamadevs.domain.Maintenance.MaintenanceType;
import calamadevs.domain.User;
import calamadevs.domain.Vehicle.Vehicle;
import calamadevs.dto.Maintenance.MaintenanceRecord.PostRequestMaintenanceRecord;
import calamadevs.dto.Maintenance.MaintenanceRecord.PutRequestMaintenanceRecord;
import calamadevs.dto.Maintenance.MaintenanceRecord.ResponseMaintenanceRecord;
import calamadevs.dto.Maintenance.MaintenanceRecordItem.RequestMaintenanceRecordItem;
import calamadevs.exception.MaintenanceNotFoundException;
import calamadevs.repository.Maintenance.MaintenanceItemRepository;
import calamadevs.repository.Maintenance.MaintenanceRecordRepository;
import calamadevs.repository.Maintenance.MaintenanceTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class MaintenanceService {

    private final MaintenanceRecordRepository maintenanceRecordRepository;
    private final MaintenanceItemRepository maintenanceItemRepository;
    private final MaintenanceTypeRepository maintenanceTypeRepository;
    private final VehicleService vehicleService;

    public MaintenanceRecord getMaintenanceOrThrow(Long id, User user) {
        return maintenanceRecordRepository.findByIdAndVehicleUser(id, user)
                .orElseThrow(() -> new MaintenanceNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Page<ResponseMaintenanceRecord> findMaintenancesByVehicle(Long vehicleId, User user, Pageable pageable) {
        Vehicle vehicle = vehicleService.getVehicleOrThrow(vehicleId, user.getId());
        return maintenanceRecordRepository.findByVehicle(vehicle, pageable)
                .map(ResponseMaintenanceRecord::new);
    }

    @Transactional(readOnly = true)
    public ResponseMaintenanceRecord getMaintenanceById(Long id, User user) {
        MaintenanceRecord maintenanceRecord = getMaintenanceOrThrow(id, user);
        return new ResponseMaintenanceRecord(maintenanceRecord);
    }

    @Transactional
    public ResponseMaintenanceRecord postMaintenance(PostRequestMaintenanceRecord dto, User user) {
        Vehicle vehicle = vehicleService.getVehicleOrThrow(dto.vehicleId(), user.getId());

        MaintenanceRecord record = new MaintenanceRecord();
        record.setVehicle(vehicle);
        record.setMaintenanceDate(dto.maintenanceDate());
        record.setMaintenanceMileage(dto.maintenanceMileage());
        record.setDescription(dto.description());
        record.setRevision(Boolean.TRUE.equals(dto.revision()));
        record.setTotalCost(resolveTotalCost(dto.totalCost(), dto.items()));

        applyItems(record, dto.items());

        MaintenanceRecord saved = maintenanceRecordRepository.save(record);
        syncVehicleMileage(vehicle, dto.maintenanceMileage());
        log.info("Maintenance created with id: {} for vehicle id: {}", saved.getId(), vehicle.getId());
        return new ResponseMaintenanceRecord(saved);
    }

    @Transactional
    public ResponseMaintenanceRecord putMaintenance(PutRequestMaintenanceRecord dto, User user) {
        MaintenanceRecord record = getMaintenanceOrThrow(dto.id(), user);

        record.setMaintenanceDate(dto.maintenanceDate());
        record.setMaintenanceMileage(dto.maintenanceMileage());
        record.setDescription(dto.description());
        record.setRevision(Boolean.TRUE.equals(dto.revision()));
        record.setTotalCost(resolveTotalCost(dto.totalCost(), dto.items()));

        record.getItems().clear();
        applyItems(record, dto.items());

        MaintenanceRecord updated = maintenanceRecordRepository.save(record);
        syncVehicleMileage(record.getVehicle(), dto.maintenanceMileage());
        log.info("Maintenance updated with id: {}", updated.getId());
        return new ResponseMaintenanceRecord(updated);
    }

    @Transactional
    public void deleteMaintenance(Long id, User user) {
        MaintenanceRecord maintenanceRecord = getMaintenanceOrThrow(id, user);
        maintenanceRecordRepository.delete(maintenanceRecord);
        log.info("Maintenance deleted with id: {}", id);
    }

    private void syncVehicleMileage(Vehicle vehicle, Double maintenanceMileage) {
        if (maintenanceMileage == null)
            return;
        double current = vehicle.getMileage() != null ? vehicle.getMileage() : 0.0;
        if (maintenanceMileage > current) {
            vehicleService.updateMileage(vehicle, maintenanceMileage);
        }
    }

    private void applyItems(MaintenanceRecord record, List<RequestMaintenanceRecordItem> itemDtos) {
        for (RequestMaintenanceRecordItem itemDto : itemDtos) {
            MaintenanceItem item = maintenanceItemRepository.findByIdWithAllowedTypes(itemDto.itemId())
                    .orElseThrow(
                            () -> new IllegalArgumentException("Invalid maintenance item ID: " + itemDto.itemId()));

            MaintenanceType type = maintenanceTypeRepository.findById(itemDto.typeId())
                    .orElseThrow(
                            () -> new IllegalArgumentException("Invalid maintenance type ID: " + itemDto.typeId()));

            boolean allowed = item.getAllowedTypes().stream()
                    .anyMatch(t -> t.getId().equals(type.getId()));
            if (!allowed) {
                throw new IllegalArgumentException(
                        "Type '" + type.getName() + "' is not allowed for item '" + item.getName() + "'");
            }

            MaintenanceRecordItem recordItem = new MaintenanceRecordItem();
            recordItem.setItem(item);
            recordItem.setType(type);
            recordItem.setCost(itemDto.cost());
            recordItem.setDescription(itemDto.description());
            record.addItem(recordItem);
        }
    }

    private BigDecimal resolveTotalCost(BigDecimal informedTotal, List<RequestMaintenanceRecordItem> items) {
        boolean allItemsHaveCost = items.stream().allMatch(i -> i.cost() != null);
        if (allItemsHaveCost) {
            BigDecimal sum = items.stream()
                    .map(RequestMaintenanceRecordItem::cost)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            return informedTotal != null ? informedTotal : sum;
        }
        if (informedTotal == null || informedTotal.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Total cost is required when one or more items do not have an individual cost");
        }
        return informedTotal;
    }
}