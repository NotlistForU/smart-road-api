package calamadevs.dto.Maintenance.MaintenanceRecord;

import calamadevs.domain.Maintenance.MaintenanceRecord;
import calamadevs.dto.Maintenance.MaintenanceRecordItem.ResponseMaintenanceRecordItem;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ResponseMaintenanceRecord(
        Long id,
        Long vehicleId,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime maintenanceDate,
        Double maintenanceMileage,
        String description,
        BigDecimal totalCost,
        boolean revision,
        List<ResponseMaintenanceRecordItem> items
) {
    public ResponseMaintenanceRecord(MaintenanceRecord m) {
        this(
                m.getId(),
                m.getVehicle().getId(),
                m.getMaintenanceDate(),
                m.getMaintenanceMileage(),
                m.getDescription(),
                m.getTotalCost(),
                m.isRevision(),
                m.getItems().stream()
                        .map(ResponseMaintenanceRecordItem::new)
                        .toList()
        );
    }
}
