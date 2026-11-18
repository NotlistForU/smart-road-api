package calamadevs.dto.VehiclesCatalog;

import calamadevs.domain.Vehicle.FuelCategory;
import calamadevs.domain.Vehicle.VehicleType;

public record VersionResponseDTO (
        Long id,
        String name,
        VehicleType vehicleType,
        FuelCategory fuelCategory,
        Double tankCapacity,
        Double batteryCapacity
){
}
