package com.gabriel.smartroad.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "maintenance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Maintenance extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Column(nullable = false, name = "maintenance_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate maintenanceDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private MaintenanceItem item;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal cost;

    @Column(nullable = false, name = "mileage_at_maintenance")
    private Integer mileageAtMaintenance;

}
