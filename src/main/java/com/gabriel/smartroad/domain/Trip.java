package com.gabriel.smartroad.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "trip")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Column(nullable = false, name = "start_location")
    private String startLocation;

    @Column(nullable = false, name = "end_location")
    private String endLocation;

    @Column(nullable = false)
    private Double distance;

    @Column(nullable = false, name = "start_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Column(nullable = false, name = "end_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
}
