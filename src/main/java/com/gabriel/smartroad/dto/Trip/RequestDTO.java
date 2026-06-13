package com.gabriel.smartroad.dto.Trip;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record RequestDTO (
        @NotNull Long vehicleId,
        @NotBlank String startLocation,
        @NotBlank String endLocation,
        @NotNull @Positive Double distance,
        @NotNull LocalDate startDate,
        @NotNull LocalDate endDate
        ){
}
