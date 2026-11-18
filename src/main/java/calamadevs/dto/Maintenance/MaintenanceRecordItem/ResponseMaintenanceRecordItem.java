package calamadevs.dto.Maintenance.MaintenanceRecordItem;

import calamadevs.domain.Maintenance.MaintenanceRecordItem;

import java.math.BigDecimal;

public record ResponseMaintenanceRecordItem(
        Long id,
        Long categoryId,
        String categoryName,
        Long itemId,
        String itemName,
        Long typeId,
        String typeCode,
        String typeName,
        BigDecimal cost,
        String description
) {
    public ResponseMaintenanceRecordItem(MaintenanceRecordItem ri) {
        this(
                ri.getId(),
                ri.getItem().getCategory().getId(),
                ri.getItem().getCategory().getName(),
                ri.getItem().getId(),
                ri.getItem().getName(),
                ri.getType().getId(),
                ri.getType().getCode(),
                ri.getType().getName(),
                ri.getCost(),
                ri.getDescription()
        );
    }
}