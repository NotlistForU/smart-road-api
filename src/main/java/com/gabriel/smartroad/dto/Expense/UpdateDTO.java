package com.gabriel.smartroad.dto.Expense;

import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateDTO(
        Long categoryId,
        @PastOrPresent LocalDate expenseDate,
        String description,
        @Positive BigDecimal cost
) {
}
