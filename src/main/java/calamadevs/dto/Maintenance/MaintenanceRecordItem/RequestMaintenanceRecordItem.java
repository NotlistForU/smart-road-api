package calamadevs.dto.Maintenance.MaintenanceRecordItem;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record RequestMaintenanceRecordItem(
        @NotNull(message = "Maintenance item ID is required")
        Long itemId,

        @NotNull(message = "Maintenance type ID is required")
        Long typeId,

        @Positive(message = "Item cost must be greater than zero")
        BigDecimal cost, // opcional (ex: item dentro de uma revisão de preço fechado)

        @Size(max = 350, message = "Description must not exceed 350 characters")
        String description
) {}