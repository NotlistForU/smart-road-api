package calamadevs.dto.Vehicle;

public record PutRequestVehicle(
    Long vehicleId,
    Long versionId,
    String vehicleNickName,
    Double mileage
) {}
