package com.gabriel.smartroad.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "expense")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Expense extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private ExpenseCategory category;

    @Column(nullable = false, name = "expense_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expenseDate;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal cost;
}
