package calamadevs.dto.Vehicle;

public record PostRequestVehicle(
    Long versionId,
    String vehicleNickName,
    Double mileage
)
{}
