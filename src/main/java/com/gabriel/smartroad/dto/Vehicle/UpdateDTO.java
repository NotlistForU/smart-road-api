package com.gabriel.smartroad.dto.Vehicle;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.PastOrPresent;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateDTO(
        // Nenhum campo tem @NotNull ou @NotBlank.
        // O que o front-end mandar, a gente atualiza. O que não mandar, a gente ignora.

        // Long brandId,
        String brandName,
        String model,
        String version,
        String lincesePlate,
        @Positive Integer manufactureYear,
        Integer modelYear,
        @PastOrPresent(message = "Purchase date cannot not be in the future") @JsonFormat(pattern = "yyyy-MM-dd") LocalDate purchaseDate,
        @PositiveOrZero BigDecimal purchasePrice,
        @Positive Integer currentMileage, // O que mais vai usar
        @Positive BigDecimal fuelTankCapacity

) {
}
