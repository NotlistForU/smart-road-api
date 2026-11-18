package calamadevs.domain.Maintenance;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "maintenance_categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 60)
    private String name;

    @Column(nullable = false)
    private boolean active = true;
}