package calamadevs.dto.Maintenance.MaintenanceSchedule;

import calamadevs.domain.Maintenance.MaintenanceSchedule;
import calamadevs.domain.Maintenance.MaintenanceScheduleStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;
public record ResponseMaintenanceSchedule(
        Long id,
        Long vehicleId,
        Long maintenanceRecordId,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime scheduledDate,
        Double scheduledOdometer,
        BigDecimal estimatedCost,
        MaintenanceScheduleStatus status,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime createdAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime updatedAt
) {
    public ResponseMaintenanceSchedule(MaintenanceSchedule s) {
        this(
                s.getId(),
                s.getVehicle().getId(),
                s.getMaintenanceRecord() != null ? s.getMaintenanceRecord().getId() : null,
                s.getScheduledDate(),
                s.getScheduledOdometer(),
                s.getEstimatedCost(),
                s.getStatus(),
                s.getCreatedAt(),
                s.getUpdatedAt()
        );
    }
}