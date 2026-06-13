package com.gabriel.smartroad.service.Crud;

import com.gabriel.smartroad.domain.*;
import com.gabriel.smartroad.dto.Trip.RequestDTO;
import com.gabriel.smartroad.dto.Trip.ResponseDTO;
import com.gabriel.smartroad.dto.Trip.UpdateDTO;
import com.gabriel.smartroad.exception.VehicleNotFoundException;
import com.gabriel.smartroad.repository.ExpenseRepository;
import com.gabriel.smartroad.repository.FuelRecordRepository;
import com.gabriel.smartroad.repository.TripRepository;
import com.gabriel.smartroad.repository.VehicleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TripService {
    private final TripRepository tripRepository;
    private final VehicleRepository vehicleRepository;
    private final ExpenseRepository expenseRepository;
    private final FuelRecordRepository fuelRecordRepository;

    public TripService(
            TripRepository tripRepository,
            VehicleRepository vehicleRepository,
            ExpenseRepository expenseRepository,
            FuelRecordRepository fuelRecordRepository
    ) {
        this.tripRepository = tripRepository;
        this.vehicleRepository = vehicleRepository;
        this.expenseRepository = expenseRepository;
        this.fuelRecordRepository = fuelRecordRepository;
    }

    public Map<String, BigDecimal> calculateTripCost(Long tripId, User user) {
        Trip trip = getTripSafe(tripId, user);

        List<FuelRecord> fuels = fuelRecordRepository.findByVehicleIdAndRefuelingDateBetween(trip.getVehicle().getId(), trip.getStartDate(), trip.getEndDate());
        BigDecimal fuelCost = fuels.stream()
                .map(FuelRecord::getTotalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<Expense> expenses = expenseRepository.findByVehicleIdAndExpenseDateBetween(trip.getVehicle().getId(), trip.getStartDate(), trip.getEndDate());
        BigDecimal expensesCost = expenses.stream()
                .map(Expense::getCost)
                .reduce(BigDecimal.ZERO,BigDecimal::add);

        BigDecimal total = fuelCost.add(expensesCost);

        Map<String, BigDecimal> report = new HashMap<>();
        report.put("tripId", BigDecimal.valueOf(tripId));
        report.put("totalFuelCost", fuelCost);
        report.put("totalExtraExpenses", expensesCost);
        report.put("totalTripCost", total);

        return report;
    }

    public ResponseDTO create(RequestDTO dto, User user) {
        Vehicle vehicle = vehicleRepository.findByIdAndUserId(dto.vehicleId(), user.getId())
                .orElseThrow(() -> new VehicleNotFoundException(dto.vehicleId().toString()));
        Trip trip = new Trip();
        trip.setVehicle(vehicle);
        trip.setStartLocation(dto.startLocation());
        trip.setEndLocation(dto.endLocation());
        trip.setDistance(dto.distance());
        trip.setStartDate(dto.startDate());
        trip.setEndDate(dto.endDate());

        Trip saved = tripRepository.save(trip);

        return toResponseDTO(saved);
    }

    public List<ResponseDTO> findByVehicleId(Long vehicleId, User user) {
        Vehicle vehicle = vehicleRepository.findByIdAndUserId(vehicleId, user.getId())
                .orElseThrow(() -> new VehicleNotFoundException(vehicleId.toString()));

        return tripRepository.findByVehicleId(vehicle.getId())
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public ResponseDTO update(Long id, UpdateDTO dto, User user) {
        Trip trip = getTripSafe(id, user);
        if (dto.startLocation() != null) trip.setStartLocation(dto.startLocation());
        if (dto.endLocation() != null) trip.setEndLocation(dto.endLocation());
        if (dto.distance() != null) trip.setDistance(dto.distance());
        if (dto.startDate() != null) trip.setStartDate(dto.startDate());
        if (dto.endDate() != null) trip.setEndDate(dto.endDate());

        tripRepository.save(trip);

        return toResponseDTO(trip);
    }

    public void delete(Long id, User user) {
        Trip trip = getTripSafe(id, user);
        tripRepository.delete(trip);
    }

    private Trip getTripSafe(Long id, User user) {
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trip not found!"));
        if (!trip.getVehicle().getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized Access");
        }

        return trip;
    }

    private ResponseDTO toResponseDTO(Trip trip) {
        return new ResponseDTO(
                trip.getId(),
                trip.getVehicle().getId(),
                trip.getStartLocation(),
                trip.getEndLocation(),
                trip.getDistance(),
                trip.getStartDate(),
                trip.getEndDate()
        );
    }
}
