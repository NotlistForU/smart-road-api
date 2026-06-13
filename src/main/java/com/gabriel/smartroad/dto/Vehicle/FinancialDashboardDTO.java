package com.gabriel.smartroad.dto.Vehicle;

import java.math.BigDecimal;

public record FinancialDashboardDTO(
        BigDecimal totalFuelCost,
        BigDecimal totalMaintenanceCost,
        BigDecimal totalTaxesAndFees,
        BigDecimal totalOverallCost,
        BigDecimal costPerKm
) {
}