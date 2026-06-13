package com.gabriel.smartroad.dto.FuelRecord;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RequestDTO(

        @NotNull(message = "Vehicle ID is required") Long vehicleId,

        @NotNull(message = "Refueling date is required") @PastOrPresent(message = "Refueling date cannot be in the future") LocalDate refuelingDate,

        @NotNull(message = "Mileage is required") @PositiveOrZero(message = "Mileage cannot be negative") Integer mileageAtRefueling,

        @NotNull(message = "Liters is required") @Positive(message = "Liters must be greater than zero") Double liters,

        @NotNull(message = "Price per liter is required") @Positive(message = "Price per liter must be greater than zero") BigDecimal pricePerLiter,

        @NotNull(message = "Total price is required") @Positive(message = "Total price must be greater than zero") BigDecimal totalPrice

) {
}
