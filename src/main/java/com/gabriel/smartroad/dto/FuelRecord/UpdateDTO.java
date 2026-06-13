package com.gabriel.smartroad.dto.FuelRecord;

import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public record UpdateDTO(
        @PastOrPresent LocalDate refuelingDate,
        @PositiveOrZero Integer mileageAtRefueling,
        @Positive Double liters) {
}
