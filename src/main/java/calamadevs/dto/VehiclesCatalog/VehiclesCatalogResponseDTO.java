package calamadevs.dto.VehiclesCatalog;

public record VehiclesCatalogResponseDTO (
        Long versionId,
        String versionName,
        String modelName,
        Double tankCapacity,
        Double batteryCapacity
)
{}
