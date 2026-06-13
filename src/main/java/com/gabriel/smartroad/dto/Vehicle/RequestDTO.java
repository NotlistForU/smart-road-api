package com.gabriel.smartroad.dto.Vehicle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gabriel.smartroad.domain.Brand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RequestDTO(
        // @NotNull(message = "Brand id is required") Long brandId,
        @NotNull(message = "Brand name is required") String brandName,
        @NotBlank(message = "Model is required") String model,
        String version,
        String licensePlate,
        // @NotNull(message = "Manufacture is required")
        @Min(value = 1885, message = "Invalid year") Integer manufactureYear,
        Integer modelYear,
        @PastOrPresent(message = "Purchase date cannot be in the future") @JsonFormat(pattern = "yyyy-MM-dd") LocalDate purchaseDate,
        @PositiveOrZero(message = "Price must be positive") BigDecimal purchasePrice,
        // @NotNull(message = "Mileage is required")
        @PositiveOrZero(message = "Mileage cannot be negative") Integer currentMileage,
        @Positive BigDecimal fuelTankCapacity,
        // @NotBlank
        String vehicleType // Para o front-end avisar se é CARRO, MOTO, etc.
) {
}
