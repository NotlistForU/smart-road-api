package com.gabriel.smartroad.repository;

import com.gabriel.smartroad.domain.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByVehicleId(Long vehicleId);

    List<Expense> findByVehicleIdAndExpenseDateBetween(Long vehicleId, LocalDate startDate, LocalDate endDate);
}
