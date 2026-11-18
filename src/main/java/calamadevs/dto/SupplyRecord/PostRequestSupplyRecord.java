package calamadevs.dto.SupplyRecord;

import calamadevs.domain.Supply.FuelType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PostRequestSupplyRecord(
        @NotNull(message = "Vehicle ID is required")
        Long vehicleId,
        @NotNull(message = "Supply date is required")
        LocalDateTime supplyDate,
        @NotNull(message = "Kilometers traveled is required") @PositiveOrZero(message = "Kilometers cannot be negative")
        Double kilometersTraveled,
        FuelType fuelType,
        @Positive(message = "Liters of fuel must be greater than zero")
        Double liters,
        @Positive(message = "Kilo watt hour must be greater than zero")
        Double kwh,
        @Positive(message = "Price per liters of fuel must be greater than zero")
        BigDecimal pricePerLiter,
        @Positive(message = "Price per kilo watt hour must be greater than zero")
        BigDecimal pricePerKwh
) {
}
