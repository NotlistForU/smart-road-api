package com.gabriel.smartroad.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FuelRecord extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "refueling_date")
    private LocalDate refuelingDate;

    @Column(name = "mileage_at_refueling")
    private Integer mileageAtRefueling;

    @Column
    private Double liters;

    @Column(name = "price_per_liter")
    private BigDecimal pricePerLiter;

    @Column(name = "total_cost")
    private BigDecimal totalCost;

    @Column(name = "discount")
    private BigDecimal discount;

    @Column(name = "kml")
    private Double kml;

    @ManyToOne
    @JoinColumn(name = "vehicle_id",  nullable = false)
    private Vehicle vehicle;
}
