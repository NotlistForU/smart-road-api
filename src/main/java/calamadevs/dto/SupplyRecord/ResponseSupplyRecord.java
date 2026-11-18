package calamadevs.dto.SupplyRecord;

import calamadevs.domain.Supply.FuelType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ResponseSupplyRecord(
                Long id, // supplyId
                Long vehicleId,
                LocalDateTime supplyDate,
                Double kilometersTraveled,
                FuelType fuelType,
                Double liters,
                Double kwh,
                BigDecimal pricePerLiter,
                BigDecimal pricePerKwh,
                BigDecimal totalCost,
                String autonomy, // 30km/liters | 30km/kwh | 300km with 30 liters and 30 kwh
                BigDecimal costPerKm
) {
}
