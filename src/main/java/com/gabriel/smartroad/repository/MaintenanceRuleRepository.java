package com.gabriel.smartroad.repository;

import com.gabriel.smartroad.domain.MaintenanceRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceRuleRepository extends JpaRepository<MaintenanceRule, Long> {
    List<MaintenanceRule> findByVehicleId(Long vehicleId);
}
