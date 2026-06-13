package com.gabriel.smartroad.repository;

import com.gabriel.smartroad.domain.MaintenanceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaintenanceItemRepository extends JpaRepository<MaintenanceItem, Long> {
    Optional<MaintenanceItem> findByName(String name);
}
