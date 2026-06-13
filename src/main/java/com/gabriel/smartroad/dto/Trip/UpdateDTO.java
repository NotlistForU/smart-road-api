package com.gabriel.smartroad.dto.Trip;

import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record UpdateDTO(
        String startLocation,
        String endLocation,
        @Positive Double distance,
        LocalDate startDate,
        LocalDate endDate
) {
}
