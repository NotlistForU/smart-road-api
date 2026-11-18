package calamadevs.domain.Maintenance;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "maintenance_record_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceRecordItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maintenance_record_id", nullable = false)
    private MaintenanceRecord maintenanceRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maintenance_type_id", nullable = false)
    private MaintenanceType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maintenance_item_id", nullable = false)
    private MaintenanceItem item;

    @Column(precision = 10, scale = 2)
    private BigDecimal cost;          // opcional (revisão com preço fechado)

    @Column(length = 350)
    private String description;       // opcional
}