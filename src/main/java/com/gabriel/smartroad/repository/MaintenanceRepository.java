package com.gabriel.smartroad.repository;

import com.gabriel.smartroad.domain.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
    List<Maintenance> findByVehicleId(Long vehicleId);

    List<Maintenance> findByVehicleIdAndMaintenanceDateBetween(Long vehicleId, LocalDate startDate, LocalDate endDate);

    // Busca a última vez que o carro trocou uma peça específica (baseado na quilometragem)
    java.util.Optional<Maintenance> findTopByVehicleIdAndItem_IdOrderByMileageAtMaintenanceDesc(Long vehicleId, Long itemId);
}
