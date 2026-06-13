package com.gabriel.smartroad.service.Crud;

import com.gabriel.smartroad.domain.Expense;
import com.gabriel.smartroad.domain.ExpenseCategory;
import com.gabriel.smartroad.domain.User;
import com.gabriel.smartroad.domain.Vehicle;
import com.gabriel.smartroad.dto.Expense.RequestDTO;
import com.gabriel.smartroad.dto.Expense.ResponseDTO;
import com.gabriel.smartroad.dto.Expense.UpdateDTO;
import com.gabriel.smartroad.exception.VehicleNotFoundException;
import com.gabriel.smartroad.repository.ExpenseCategoryRepository;
import com.gabriel.smartroad.repository.ExpenseRepository;
import com.gabriel.smartroad.repository.VehicleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final VehicleRepository vehicleRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;

    public ExpenseService(ExpenseRepository expenseRepository, VehicleRepository vehicleRepository, ExpenseCategoryRepository expenseCategoryRepository) {
        this.expenseRepository = expenseRepository;
        this.vehicleRepository = vehicleRepository;
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    public ResponseDTO create(RequestDTO dto, User user) {
        Vehicle vehicle = vehicleRepository.findByIdAndUserId(dto.vehicleId(), user.getId())
                .orElseThrow(() -> new VehicleNotFoundException(dto.vehicleId().toString()));

        ExpenseCategory category = getCategorySafe(dto.categoryId());
        Expense expense = new Expense();

        expense.setCategory(category);
        expense.setVehicle(vehicle);
        expense.setExpenseDate(dto.expenseDate());
        expense.setDescription(dto.description());
        expense.setCost(dto.cost());

        Expense saved = expenseRepository.save(expense);
        return toResponseDTO(saved);
    }

    public List<ResponseDTO> findByVehicleId(Long vehicleId, User user) {
        Vehicle vehicle = vehicleRepository.findByIdAndUserId(vehicleId, user.getId())
                .orElseThrow(() -> new VehicleNotFoundException(vehicleId.toString()));

        return expenseRepository.findByVehicleId(vehicle.getId())
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }


    public ResponseDTO update(Long id, UpdateDTO dto, User user) {
        Expense expense = getExpenseSafe(id, user);
        if (dto.categoryId() != null) {
            ExpenseCategory category = getCategorySafe(dto.categoryId());
            expense.setCategory(category);
        }
        if (dto.expenseDate() != null) expense.setExpenseDate(dto.expenseDate());
        if (dto.description() != null) expense.setDescription(dto.description());
        if (dto.cost() != null) expense.setCost(dto.cost());
        expenseRepository.save(expense);
        return toResponseDTO(expense);
    }

    public void delete(Long id, User user) {
        Expense expense = getExpenseSafe(id, user);
        expenseRepository.delete(expense);
    }

    private Expense getExpenseSafe(Long id, User user) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found!"));

        if (!expense.getVehicle().getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized Access");
        }

        return expense;
    }

    private ExpenseCategory getCategorySafe(Long id){
        return expenseCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found!"));
    }

    private ResponseDTO toResponseDTO(Expense expense) {
        return new ResponseDTO(
                expense.getId(),
                expense.getVehicle().getId(),
                expense.getCategory().getId(),
                expense.getCategory().getName(),
                expense.getExpenseDate(),
                expense.getDescription(),
                expense.getCost()
        );
    }
}
