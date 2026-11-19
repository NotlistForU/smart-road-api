package calamadevs.dto.PublicDashboard;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import calamadevs.dto.PublicDashboard.ResponsePublicDashboard.MaintenanceMetrics;

/// - SupplyMetrics
/// - MaintenanceMetrics
public record ResponsePublicDashboard(
                List<SupplyMetrics> supplies,
                List<MaintenanceMetrics> maintenances) {
        public record SupplyMetrics(
                        LocalDate supplyDate,
                        BigDecimal costPerKm,
                        BigDecimal kmPerLiter,
                        BigDecimal kmPerKwh,
                        BigDecimal kmPerMj,
                        BigDecimal key,
                        BigDecimal mjPerLiter,
                        BigDecimal mjPerKwh,
                        Double kilometersTraveled,
                        Double liters,
                        Double kwh,
                        String fuelType,
                        BigDecimal totalCost) {
        }

        public record MaintenanceMetrics(
                        Double maintenanceMileage,
                        BigDecimal totalPrice,
                        boolean revision,
                        List<MaintenanceItemMetrics> items) {
        }

        public record MaintenanceItemMetrics(
                        String tagName,
                        BigDecimal cost) {
        }
}