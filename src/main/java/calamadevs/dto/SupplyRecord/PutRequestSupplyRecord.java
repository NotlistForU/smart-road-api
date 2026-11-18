package calamadevs.dto.SupplyRecord;

import calamadevs.domain.Supply.FuelType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PutRequestSupplyRecord(
        @NotNull(message = "Supply vehicle id is required") Long id,
        @NotNull(message = "Vehicle id is required") Long vehicleId,
        @PastOrPresent(message = "Supply date cannot be in the future") LocalDateTime supplyDate,
        @PositiveOrZero(message = "Mileage cannot be negative") Double kilometersTraveled,
        FuelType fuelType,
        @Positive(message = "Liters must be greater than zero") Double liters,
        @Positive(message = "Kilo watt hour must be greater than zero") Double kwh,
        @Positive(message = "Price per liter must be greater than zero") BigDecimal pricePerLiter,
        @Positive(message = "Price per kilo watt hour must be greater than zero") BigDecimal pricePerKwh) {
}
