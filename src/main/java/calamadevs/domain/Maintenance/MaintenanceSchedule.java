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

@Entity
@Table(name = "maintenance_schedules")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceSchedule extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maintenance_record_id", nullable = true, unique = true)
    private MaintenanceRecord maintenanceRecord;

    @Column(name = "scheduled_date")
    private LocalDateTime scheduledDate;

    @Column(name = "scheduled_odometer")
    private Double scheduledOdometer;

    @Column(name = "estimated_cost")
    private BigDecimal estimatedCost;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private MaintenanceScheduleStatus status = MaintenanceScheduleStatus.PENDING;
}