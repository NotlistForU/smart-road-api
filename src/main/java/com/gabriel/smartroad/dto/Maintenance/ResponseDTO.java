package com.gabriel.smartroad.dto.Maintenance;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ResponseDTO (
        Long id,
        Long vehicleId,
        LocalDate maintenanceDate,
        Long idItem,
        String itemName,
        String description,
        BigDecimal cost,
        Integer mileageAtMaintenance
){
}
