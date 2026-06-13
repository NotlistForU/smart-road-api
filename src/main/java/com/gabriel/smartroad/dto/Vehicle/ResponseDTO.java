package com.gabriel.smartroad.dto.Vehicle;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ResponseDTO(
          Long id,
          // Long brandId,
          String brandName,
          String model,
          String version,
          String licensePlate,
          Integer manufactureYear,
          LocalDate purchaseDate,
          BigDecimal purchasePrice,
          Integer currentMileage,
          BigDecimal fuelTankCapacity,
          Boolean active) {
}
