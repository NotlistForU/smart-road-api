package com.gabriel.smartroad.dto.Expense;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ResponseDTO(
        Long id,
        Long vehicleId,
        Long categoryId,
        String categoryName,
        LocalDate expenseDate,
        String description,
        BigDecimal cost
) {
}
