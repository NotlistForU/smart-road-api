package com.gabriel.smartroad.repository;

import com.gabriel.smartroad.domain.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Long> {
    List<ExpenseCategory> findByUserIdIsNullOrUserId(Long userId);
}
