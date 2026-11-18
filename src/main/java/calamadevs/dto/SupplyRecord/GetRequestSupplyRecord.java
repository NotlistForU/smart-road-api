package calamadevs.dto.SupplyRecord;

import jakarta.validation.constraints.NotNull;

public record GetRequestSupplyRecord(
        @NotNull(message = "Vehicle id is required")
        Long vehicleId
) {
}
