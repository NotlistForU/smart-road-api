package calamadevs.dto.Maintenance.MaintenanceRecord;

import calamadevs.dto.Maintenance.MaintenanceRecordItem.RequestMaintenanceRecordItem;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PostRequestMaintenanceRecord(
        @NotNull(message = "Vehicle ID is required")
        Long vehicleId,
        @NotNull(message = "Maintenance date is required")
        @PastOrPresent(message = "Maintenance date cannot be in the future")
        LocalDateTime maintenanceDate,
        @NotNull(message = "Maintenance mileage is required")
        @PositiveOrZero(message = "Maintenance mileage cannot be negative")
        Double maintenanceMileage,
        @Size(max = 350, message = "Description must not exceed 350 characters")
        String description,
        @Positive(message = "Total cost must be greater than zero")
        BigDecimal totalCost,
        Boolean revision,
        @NotEmpty(message = "At least one maintenance item is required")
        List<@Valid RequestMaintenanceRecordItem> items
) {}
