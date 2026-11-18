package calamadevs.dto.Vehicle;

import calamadevs.domain.Vehicle.FuelCategory;
import calamadevs.domain.Vehicle.VehicleType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ResponseVehicle(
        Long vehicleId,
        Long versionId,
        String vehicleNickName,
        String versionName,
        String brandName,
        String modelName,
        Integer modelYear,
        VehicleType vehicleType,
        FuelCategory fuelCategory,
        Double tankCapacity,
        Double batteryCapacity,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime createAt,
        Double mileage) {
}
