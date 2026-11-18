package calamadevs.dto.Maintenance.MaintenanceCatalog;

import java.util.List;

public record ResponseMaintenanceItem (
        Long id,
        Long categoryId,
        String categoryName,
        String name,
        List<ResponseMaintenanceType> allowedTypes
) {
}
