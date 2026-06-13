package com.gabriel.smartroad.service.Crud;

import com.gabriel.smartroad.domain.Maintenance;
import com.gabriel.smartroad.domain.MaintenanceItem;
import com.gabriel.smartroad.domain.User;
import com.gabriel.smartroad.domain.Vehicle;
import com.gabriel.smartroad.dto.Maintenance.RequestDTO;
import com.gabriel.smartroad.dto.Maintenance.ResponseDTO;
import com.gabriel.smartroad.dto.Maintenance.UpdateDTO;
import com.gabriel.smartroad.exception.VehicleNotFoundException;
import com.gabriel.smartroad.repository.MaintenanceItemRepository;
import com.gabriel.smartroad.repository.MaintenanceRepository;
import com.gabriel.smartroad.repository.VehicleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MaintenanceService {
    private final MaintenanceRepository maintenanceRepository;
    private final MaintenanceItemRepository maintenanceItemRepository;
    private final VehicleRepository vehicleRepository;

    public MaintenanceService(MaintenanceRepository maintenanceRepository, VehicleRepository vehicleRepository, MaintenanceItemRepository maintenanceItemRepository) {
        this.maintenanceRepository = maintenanceRepository;
        this.vehicleRepository = vehicleRepository;
        this.maintenanceItemRepository = maintenanceItemRepository;
    }

    public ResponseDTO create(RequestDTO dto, User user) {
        Vehicle vehicle = vehicleRepository
                .findByIdAndUserId(dto.vehicleId(), user.getId())
                .orElseThrow(() -> new VehicleNotFoundException(dto.vehicleId().toString()));


        Maintenance maintenance = new Maintenance();
        MaintenanceItem item = getItemSafe(dto.itemId());

        maintenance.setVehicle(vehicle);
        maintenance.setMaintenanceDate(dto.maintenanceDate());
        maintenance.setItem(item);
        maintenance.setDescription(dto.description());
        maintenance.setCost(dto.cost());
        maintenance.setMileageAtMaintenance(dto.mileageAtMaintenance());

        if (vehicle.getCurrentMileage() == null || maintenance.getMileageAtMaintenance() > vehicle.getCurrentMileage()) {
            vehicle.setCurrentMileage(maintenance.getMileageAtMaintenance());
            vehicleRepository.save(vehicle);
        }

        Maintenance saved = maintenanceRepository.save(maintenance);

        return toResponseDTO(saved);
    }

    public List<ResponseDTO> findByVehicleId(Long vehicleId, User user) {
        Vehicle vehicle = vehicleRepository.findByIdAndUserId(vehicleId, user.getId())
                .orElseThrow(() -> new VehicleNotFoundException(vehicleId.toString()));

        return maintenanceRepository.findByVehicleId(vehicle.getId())
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public ResponseDTO update(Long id, UpdateDTO dto, User user) {
        Maintenance maintenance = getMaintenanceSafe(id, user);


        if (dto.maintenanceDate() != null) maintenance.setMaintenanceDate(dto.maintenanceDate());
        if (dto.idItem() != null){
            MaintenanceItem item = getItemSafe(dto.idItem());
            maintenance.setItem(item);
        }
        if (dto.description() != null) maintenance.setDescription(dto.description());
        if (dto.cost() != null) maintenance.setCost(dto.cost());
        if (dto.mileageAtMaintenance() != null) maintenance.setMileageAtMaintenance(dto.mileageAtMaintenance());

        maintenanceRepository.save(maintenance);
        return toResponseDTO(maintenance);
    }

    public void delete(Long id, User user) {
        Maintenance maintenance = getMaintenanceSafe(id, user);
        maintenanceRepository.delete(maintenance);
    }

    private Maintenance getMaintenanceSafe(Long id, User user) {
        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance not found"));
        if (!maintenance.getVehicle().getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized Access!");
        }
        return maintenance;
    }

    private  MaintenanceItem getItemSafe(Long id) {
        return  maintenanceItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found in Database"));

    }

    private ResponseDTO toResponseDTO(Maintenance maintenance) {
        return new ResponseDTO(
                maintenance.getId(),
                maintenance.getVehicle().getId(),
                maintenance.getMaintenanceDate(),
                maintenance.getItem().getId(),
                maintenance.getItem().getName(),
                maintenance.getDescription(),
                maintenance.getCost(),
                maintenance.getMileageAtMaintenance()
        );
    }
}
