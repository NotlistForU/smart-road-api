package com.gabriel.smartroad.repository;

import com.gabriel.smartroad.domain.FuelRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FuelRecordRepository extends JpaRepository<FuelRecord, Long> {
    List<FuelRecord> findByVehicleId(Long vehicleId);

    @Query("SELECT f.kml FROM FuelRecord f JOIN f.vehicle v JOIN v.user u WHERE LOWER(v.model) = LOWER(:model) AND LOWER(v.version) = LOWER(:version) AND u.isTrustedSource = true AND f.kml IS NOT NULL")
    List<Double> findTrustedKmlByVehicleModel(@Param("model") String model, @Param("version") String version);

    List<FuelRecord> findByVehicleIdAndRefuelingDateBetween(Long vehicleId, LocalDate startDate, LocalDate endDate);

}
