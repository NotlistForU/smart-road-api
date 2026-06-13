package com.gabriel.smartroad.dto.Maintenance;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RequestDTO(
        @NotNull Long vehicleId,
        @NotNull @PastOrPresent LocalDate maintenanceDate,
        @NotNull Long itemId,
        @NotBlank String description,
        @NotNull @Positive BigDecimal cost,
        @NotNull @PositiveOrZero Integer mileageAtMaintenance
) {
}
