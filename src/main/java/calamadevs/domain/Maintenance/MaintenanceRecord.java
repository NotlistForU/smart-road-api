package calamadevs.domain.Maintenance;

import calamadevs.domain.AuditableEntity;
import calamadevs.domain.Vehicle.Vehicle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "maintenance_records")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceRecord extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double maintenanceMileage;

    @Column(nullable = false)
    private LocalDateTime maintenanceDate;

    @Column(length = 350)
    private String description;

    @Column(nullable = false)
    private BigDecimal totalCost;

    @Column(nullable = false)
    private boolean revision = false;
    @OneToMany(mappedBy = "maintenanceRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MaintenanceRecordItem> items = new ArrayList<>();

    public void addItem(MaintenanceRecordItem item) {
        item.setMaintenanceRecord(this);
        items.add(item);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;
}
