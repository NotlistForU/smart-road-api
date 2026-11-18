package calamadevs.dto.Maintenance.MaintenanceSchedule;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PostRequestMaintenanceSchedule (
        @NotNull(message = "Vehicle ID is required")
        Long vehicleId,
        Long maintenanceRecordId,
        LocalDateTime scheduledDate,
        @PositiveOrZero(message = "Scheduled odometer cannot be negative")
        Double scheduledOdometer,
        @Positive(message = "Estimated cost must be greater than zero")
        BigDecimal estimatedCost
) {
}
