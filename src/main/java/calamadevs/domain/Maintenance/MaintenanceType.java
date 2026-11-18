package calamadevs.domain.Maintenance;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "maintenance_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 40)
    private String code;   // ex: TROCA (estável, nunca muda)

    @Column(nullable = false, length = 60)
    private String name;   // ex: Troca (texto exibido)

    @Column(nullable = false)
    private boolean active = true;
}