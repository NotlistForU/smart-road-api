package com.gabriel.smartroad.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@DiscriminatorValue("CAR") // É isso que vai ser salvo na coluna 'tipo_veiculo'
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Car extends Vehicle {

    // Atributos exclusivos de um carro
    @Column(name = "number_of_doors")
    private Integer numberOfDoors;

    @Column(name = "body_type") // Ex: Sedan, Hatch, SUV, Picape, Cupê
    private String bodyType;
}
