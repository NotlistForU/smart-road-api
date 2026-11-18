package calamadevs.dto.Vehicle;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record PutResponseVehicle(
        Long vehicleId,
        Long versionId,
        String vehicleNickName,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss")
        LocalDateTime updateAt
){
}
