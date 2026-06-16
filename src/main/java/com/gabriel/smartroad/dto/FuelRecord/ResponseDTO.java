package com.gabriel.smartroad.dto.FuelRecord;

import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ResponseDTO(

        Long id,
        Long vehicleId,
        LocalDate refuelingDate,
        Integer mileageAtRefueling,
        Double liters,
        BigDecimal pricePerLiter,
        BigDecimal totalCost,
        BigDecimal discount,
        Double kml

) {
}
