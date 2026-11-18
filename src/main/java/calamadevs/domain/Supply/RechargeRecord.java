package calamadevs.domain.Supply;

import jakarta.persistence.*;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "recharges_record")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RechargeRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kwh")
    private Double kwh;

    @Column(name = "price_per_kwh")
    private BigDecimal pricePerKwh;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "supply_id",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(
                    foreignKeyDefinition = "FOREIGN KEY (supply_id) REFERENCES supply_record(id) ON DELETE CASCADE"
            )
    )
    private SupplyRecord supply;
}
