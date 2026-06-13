package com.gabriel.smartroad.dto.Trip;

import java.time.LocalDate;

public record ResponseDTO(
        Long id,
        Long vehicleId,
        String startLocation,
        String endLocation,
        Double distance,
        LocalDate startDate,
        LocalDate endDate
) {
}
