package com.gabriel.smartroad.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehicle")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "vehicle_type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public abstract class Vehicle extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "brand_id", nullable = false)
    // private Brand brand;

    @Column(name = "brand_name", nullable = false)
    private String brandName;

    @Column(nullable = false)
    private String model;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<FuelRecord> fuelRecordList = new ArrayList<>();

    @Column
    private String version;

    @Column(unique = true)
    private String licensePlate;

    @Column(name = "manufacture_year")
    private Integer manufactureYear;

    @Column(name = "model_year")
    private Integer modelYear; // Buscar na tabela FIPE

    @PastOrPresent(message = "Purchase cannot not be in the future")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "purchase_price")
    private BigDecimal purchasePrice;

    @Column(name = "current_mileage")
    private Integer currentMileage;

    @Column(name = "fuel_tank_capacity")
    private BigDecimal fuelTankCapacity;

    @Column(nullable = false)
    private Boolean active = true; // Exclusão lógica: inativa em vez de deletar do banco

}
