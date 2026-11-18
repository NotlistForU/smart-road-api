package calamadevs.domain.Supply;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FuelRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type")
    private FuelType fuelType;

    @Column
    private Double liters;

    @Column(name = "price_per_liter")
    private BigDecimal pricePerLiter;

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
