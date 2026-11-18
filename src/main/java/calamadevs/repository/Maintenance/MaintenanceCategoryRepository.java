package calamadevs.repository.Maintenance;
import calamadevs.domain.Maintenance.MaintenanceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface MaintenanceCategoryRepository extends JpaRepository<MaintenanceCategory, Long> {
    List<MaintenanceCategory> findByActiveTrueOrderByNameAsc();
}