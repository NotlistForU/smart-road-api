package calamadevs.domain.Maintenance;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "maintenance_item_aliases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceItemAlias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private MaintenanceItem item;

    @Column(nullable = false, length = 100)
    private String alias;            // ex: "Para choque"

    @Column(nullable = false, unique = true, length = 100)
    private String normalizedAlias;  // ex: "para choque"
}