package com.gabriel.smartroad.service.Crud;

import com.gabriel.smartroad.domain.*;
import com.gabriel.smartroad.dto.Vehicle.FinancialDashboardDTO;
import com.gabriel.smartroad.dto.Vehicle.RequestDTO;
import com.gabriel.smartroad.dto.Vehicle.ResponseDTO;
import com.gabriel.smartroad.dto.Vehicle.UpdateDTO;
import com.gabriel.smartroad.exception.VehicleNotFoundException;
import com.gabriel.smartroad.repository.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
// import java.util.List;
// import java.util.stream.Collectors;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    // private final BrandRepository brandRepository;
    private final FuelRecordRepository fuelRecordRepository;
    private final MaintenanceRepository maintenanceRepository;
    private final ExpenseRepository expenseRepository;

    public VehicleService(
            VehicleRepository vehicleRepository,
            BrandRepository brandRepository,
            FuelRecordRepository fuelRecordRepository,
            MaintenanceRepository maintenanceRepository,
            ExpenseRepository expenseRepository) {
        this.vehicleRepository = vehicleRepository;
        // this.brandRepository = brandRepository;
        this.fuelRecordRepository = fuelRecordRepository;
        this.maintenanceRepository = maintenanceRepository;
        this.expenseRepository = expenseRepository;
    }

    // 1. SALVAR (CRIAR)
    public ResponseDTO create(RequestDTO dto, User user) {

        // Brand brand = brandRepository.findById(dto.brandId())
        // .orElseThrow(() -> new RuntimeException("Brand not found"));
        Vehicle vehicle;

        // Decide qual classe filha instanciar (Polimorfismo)
        switch (dto.vehicleType().toUpperCase()) {
            case "CAR":
                vehicle = new Car();
                break;
            // case "MOTO": vehicle = new Moto(); break;
            default:
                throw new IllegalArgumentException("Invalid vehicle type: " + dto.vehicleType());
        }

        // Lendo do Record (sem "get") e setando na Entidade
        vehicle.setUser(user);
        vehicle.setBrandName(dto.brandName());
        vehicle.setModel(dto.model());
        vehicle.setVersion(dto.version());
        vehicle.setLicensePlate(dto.licensePlate());
        vehicle.setManufactureYear(dto.manufactureYear());
        vehicle.setPurchaseDate(dto.purchaseDate());
        vehicle.setPurchasePrice(dto.purchasePrice());
        vehicle.setCurrentMileage(dto.currentMileage());
        vehicle.setFuelTankCapacity(dto.fuelTankCapacity());
        vehicle.setActive(true);

        Vehicle saved = vehicleRepository.save(vehicle);

        return toResponseDTO(saved);
    }

    // 2. BUSCAR TODOS
    public Page<ResponseDTO> findAll(Pageable pageable) {
        return vehicleRepository.findAll(pageable).map(this::toResponseDTO);
    }

    public Page<ResponseDTO> findAllByUser(User user, Pageable pageable) {
        return vehicleRepository.findByUserIdAndActiveTrue(user.getId(), pageable)
                .map(this::toResponseDTO);
    }

    // 3. BUSCAR POR ID
    public ResponseDTO findById(Long id, User user) {
        Vehicle vehicle = getVehicleOrThrow(id, user.getId());
        return toResponseDTO(vehicle);
    }

    // 4. ATUALIZAR (PARCIAL)
    public ResponseDTO update(Long id, UpdateDTO dto, User user) {

        Vehicle vehicle = getVehicleOrThrow(id, user.getId());
        // if (dto.brandId() != null) {
        // Brand brand = brandRepository.findById(dto.brandId())
        // .orElseThrow(() -> new RuntimeException("Brand not found"));
        // vehicle.setBrand((brand));
        // }
        if (dto.brandName() != null) {
            vehicle.setBrandName(dto.brandName());
        }

        // Só atualiza o que o usuário enviou (não for nulo)
        if (dto.model() != null)
            vehicle.setModel(dto.model());
        if (dto.version() != null)
            vehicle.setVersion(dto.version());
        if (dto.lincesePlate() != null)
            vehicle.setLicensePlate(dto.lincesePlate());
        if (dto.manufactureYear() != null)
            vehicle.setManufactureYear(dto.manufactureYear());
        if (dto.purchasePrice() != null)
            vehicle.setPurchasePrice(dto.purchasePrice());
        if (dto.fuelTankCapacity() != null)
            vehicle.setFuelTankCapacity(dto.fuelTankCapacity());
        if (dto.currentMileage() != null)
            vehicle.setCurrentMileage(dto.currentMileage());

        Vehicle updated = vehicleRepository.save(vehicle);
        return toResponseDTO(updated);
    }

    // 5. MAPEAMENTO PARA RESPONSE DTO
    public ResponseDTO toResponseDTO(Vehicle vehicle) {
        return new ResponseDTO(
                vehicle.getId(),
                // vehicle.getBrand().getId(),
                // vehicle.getBrand().getName(),
                vehicle.getBrandName(),
                vehicle.getModel(),
                vehicle.getVersion(),
                vehicle.getLicensePlate(),
                vehicle.getManufactureYear(),
                vehicle.getPurchaseDate(),
                vehicle.getPurchasePrice(),
                vehicle.getCurrentMileage(),
                vehicle.getFuelTankCapacity(),
                vehicle.getActive());
    }

    // 6. SOFT DELETE -> exclusão lógica.
    public void delete(Long id, User user) {
        Vehicle vehicle = getVehicleOrThrow(id, user.getId());
        // Em vez de .delete(veichile) apenas desativa.
        vehicle.setActive(false);
        vehicleRepository.save(vehicle);
    }

    public FinancialDashboardDTO getFinancialDashboard(Long vehicleId, User user, LocalDate startDate,
            LocalDate endDate) {
        Vehicle vehicle = getVehicleOrThrow(vehicleId, user.getId());

        BigDecimal totalFuel;
        BigDecimal totalMaintenance;
        BigDecimal totalExpenses;

        if (startDate != null && endDate != null) {
            totalFuel = fuelRecordRepository.findByVehicleIdAndRefuelingDateBetween(vehicleId, startDate, endDate)
                    .stream()
                    .map(FuelRecord::getTotalCost)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            totalMaintenance = maintenanceRepository
                    .findByVehicleIdAndMaintenanceDateBetween(vehicleId, startDate, endDate)
                    .stream()
                    .map(Maintenance::getCost)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            totalExpenses = expenseRepository.findByVehicleIdAndExpenseDateBetween(vehicleId, startDate, endDate)
                    .stream()
                    .map(Expense::getCost)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            totalFuel = fuelRecordRepository.findByVehicleId(vehicleId)
                    .stream()
                    .map(FuelRecord::getTotalCost)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            totalMaintenance = maintenanceRepository.findByVehicleId(vehicleId)
                    .stream()
                    .map(Maintenance::getCost)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            totalExpenses = expenseRepository.findByVehicleId(vehicleId)
                    .stream()
                    .map(Expense::getCost)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        BigDecimal totalOverall = totalFuel.add(totalMaintenance).add(totalExpenses);
        BigDecimal costPerKm = BigDecimal.ZERO;

        if (vehicle.getCurrentMileage() != null && vehicle.getCurrentMileage() > 0) {
            costPerKm = totalOverall.divide(BigDecimal.valueOf(vehicle.getCurrentMileage()), 2, RoundingMode.HALF_UP);
        }

        return new FinancialDashboardDTO(
                totalFuel,
                totalMaintenance,
                totalExpenses,
                totalOverall,
                costPerKm);
    }

    // Função auxiliar
    private Vehicle getVehicleOrThrow(Long id, Long userId) {
        return vehicleRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle ID: " + id + " not found"));
    }
}