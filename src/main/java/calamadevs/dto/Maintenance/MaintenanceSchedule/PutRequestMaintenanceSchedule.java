package calamadevs.dto.Maintenance.MaintenanceSchedule;

import calamadevs.domain.Maintenance.MaintenanceScheduleStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PutRequestMaintenanceSchedule(
        @NotNull(message = "Schedule ID is required")
        Long id,
        Long maintenanceRecordId,
        LocalDateTime scheduledDate,
        @PositiveOrZero(message = "Scheduled odometer cannot be negative")
        Double scheduledOdometer,
        @Positive(message = "Estimated cost must be greater than zero")
        BigDecimal estimatedCost,
        @NotNull(message = "Status is required")
        MaintenanceScheduleStatus status
) {}