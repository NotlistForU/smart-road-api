package com.gabriel.smartroad.dto.Expense;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RequestDTO(
        @NotNull Long vehicleId,
        @NotNull Long categoryId,
        @NotNull @PastOrPresent LocalDate expenseDate,
        @NotBlank String description,
        @NotNull @Positive BigDecimal cost
) {
}
