package calamadevs.service;

import calamadevs.domain.Maintenance.MaintenanceRecord;
import calamadevs.domain.Maintenance.MaintenanceSchedule;
import calamadevs.domain.Maintenance.MaintenanceScheduleStatus;
import calamadevs.domain.User;
import calamadevs.domain.Vehicle.Vehicle;
import calamadevs.dto.Maintenance.MaintenanceSchedule.PostRequestMaintenanceSchedule;
import calamadevs.dto.Maintenance.MaintenanceSchedule.PutRequestMaintenanceSchedule;
import calamadevs.dto.Maintenance.MaintenanceSchedule.ResponseMaintenanceSchedule;
import calamadevs.exception.MaintenanceNotFoundException;
import calamadevs.repository.Maintenance.MaintenanceScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class MaintenanceScheduleService {
    private final MaintenanceScheduleRepository scheduleRepository;
    private final MaintenanceService maintenanceService;
    private final VehicleService vehicleService;
    public MaintenanceSchedule getScheduleOrThrow(Long id, User user) {
        return scheduleRepository.findByIdAndVehicleUser(id, user)
                .orElseThrow(() -> new MaintenanceNotFoundException("Maintenance schedule not found with id: " + id));
    }
    @Transactional(readOnly = true)
    public List<ResponseMaintenanceSchedule> findSchedulesByVehicle(Long vehicleId, User user) {
        Vehicle vehicle = vehicleService.getVehicleOrThrow(vehicleId, user.getId());
        return scheduleRepository.findByVehicleOrderByScheduledDateAsc(vehicle)
                .stream()
                .map(ResponseMaintenanceSchedule::new)
                .toList();
    }
    @Transactional(readOnly = true)
    public ResponseMaintenanceSchedule getScheduleById(Long id, User user) {
        return new ResponseMaintenanceSchedule(getScheduleOrThrow(id, user));
    }
    @Transactional
    public ResponseMaintenanceSchedule postSchedule(PostRequestMaintenanceSchedule dto, User user) {
        validateScheduleTarget(dto.scheduledDate(), dto.scheduledOdometer());
        Vehicle vehicle = vehicleService.getVehicleOrThrow(dto.vehicleId(), user.getId());
        MaintenanceSchedule schedule = new MaintenanceSchedule();
        schedule.setVehicle(vehicle);
        schedule.setScheduledDate(dto.scheduledDate());
        schedule.setScheduledOdometer(dto.scheduledOdometer());
        schedule.setEstimatedCost(dto.estimatedCost());
        schedule.setStatus(MaintenanceScheduleStatus.PENDING);
        if (dto.maintenanceRecordId() != null) {
            MaintenanceRecord record = resolveMaintenanceRecord(dto.maintenanceRecordId(), vehicle, user, null);
            schedule.setMaintenanceRecord(record);
        }
        MaintenanceSchedule saved = scheduleRepository.save(schedule);
        log.info("Maintenance schedule created with id: {} for vehicle id: {}", saved.getId(), vehicle.getId());
        return new ResponseMaintenanceSchedule(saved);
    }
    @Transactional
    public ResponseMaintenanceSchedule putSchedule(PutRequestMaintenanceSchedule dto, User user) {
        validateScheduleTarget(dto.scheduledDate(), dto.scheduledOdometer());
        MaintenanceSchedule schedule = getScheduleOrThrow(dto.id(), user);
        schedule.setScheduledDate(dto.scheduledDate());
        schedule.setScheduledOdometer(dto.scheduledOdometer());
        schedule.setEstimatedCost(dto.estimatedCost());
        schedule.setStatus(dto.status());
        if (dto.maintenanceRecordId() != null) {
            MaintenanceRecord record = resolveMaintenanceRecord(
                    dto.maintenanceRecordId(),
                    schedule.getVehicle(),
                    user,
                    schedule.getId()
            );
            schedule.setMaintenanceRecord(record);
        } else {
            schedule.setMaintenanceRecord(null);
        }
        MaintenanceSchedule updated = scheduleRepository.save(schedule);
        log.info("Maintenance schedule updated with id: {}", updated.getId());
        return new ResponseMaintenanceSchedule(updated);
    }
    @Transactional
    public void deleteSchedule(Long id, User user) {
        MaintenanceSchedule schedule = getScheduleOrThrow(id, user);
        scheduleRepository.delete(schedule);
        log.info("Maintenance schedule deleted with id: {}", id);
    }
    private void validateScheduleTarget(LocalDateTime scheduledDate, Double scheduledOdometer) {
        if (scheduledDate == null && scheduledOdometer == null) {
            throw new IllegalArgumentException("At least one of scheduledDate or scheduledOdometer must be provided");
        }
    }
    private MaintenanceRecord resolveMaintenanceRecord(
            Long maintenanceRecordId,
            Vehicle vehicle,
            User user,
            Long currentScheduleId
    ) {
        MaintenanceRecord record = maintenanceService.getMaintenanceOrThrow(maintenanceRecordId, user);
        if (!record.getVehicle().getId().equals(vehicle.getId())) {
            throw new IllegalArgumentException("Maintenance record does not belong to the scheduled vehicle");
        }
        boolean alreadyLinkedToAnother = scheduleRepository.existsByMaintenanceRecordId(maintenanceRecordId)
                && (currentScheduleId == null || scheduleRepository.findById(currentScheduleId)
                .map(s -> s.getMaintenanceRecord() == null || !s.getMaintenanceRecord().getId().equals(maintenanceRecordId))
                .orElse(true));
        if (alreadyLinkedToAnother) {
            throw new IllegalArgumentException("Maintenance record is already linked to another schedule");
        }
        return record;
    }
}