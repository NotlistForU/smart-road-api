package com.gabriel.smartroad.dto.Maintenance;

import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateDTO(
        @PastOrPresent LocalDate maintenanceDate,
        Long idItem,
        String description,
        @Positive BigDecimal cost,
        @PositiveOrZero Integer mileageAtMaintenance
) {
}
