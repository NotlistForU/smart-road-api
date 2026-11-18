package calamadevs.repository.Maintenance;

import calamadevs.domain.Maintenance.MaintenanceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public interface MaintenanceTypeRepository extends JpaRepository<MaintenanceType, Long> {
    List<MaintenanceType> findByActiveTrueOrderByNameAsc();
    Optional<MaintenanceType> findByCode(String code);
}