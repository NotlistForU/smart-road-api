package calamadevs.dto.SupplyRecord;

import jakarta.validation.constraints.NotNull;

public record DeleteRequestSupplyRecord(
        @NotNull(message = "Supply vehicle id is required")
        Long id,
        @NotNull(message = "Vehicle id is required")
        Long vehicleId
) {
}
