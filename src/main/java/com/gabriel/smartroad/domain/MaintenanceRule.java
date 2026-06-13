package com.gabriel.smartroad.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "maintenance_rule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceRule extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private MaintenanceItem item;

    @Column(name="interval_km", nullable = false)
    private Integer intervalKm;

    @Column(name = "interval_months")
    private Integer intervalMonths;
}
