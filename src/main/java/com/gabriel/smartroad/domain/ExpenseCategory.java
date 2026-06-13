package com.gabriel.smartroad.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "expense_category")
@Entity
public class ExpenseCategory extends  AuditableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;


    // If the user is NULL, it's a system category (Vehicle Tax, Insurance), for everyone to see.
    // If it's NOT NULL, it's an exclusive category that the user invented
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

}
