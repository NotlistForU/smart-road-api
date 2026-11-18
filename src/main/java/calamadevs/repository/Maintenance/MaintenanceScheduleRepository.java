package calamadevs.repository.Maintenance;

import calamadevs.domain.Maintenance.MaintenanceSchedule;
import calamadevs.domain.User;
import calamadevs.domain.Vehicle.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaintenanceScheduleRepository extends JpaRepository<MaintenanceSchedule, Long> {

    List<MaintenanceSchedule> findByVehicleOrderByScheduledDateAsc(Vehicle vehicle);

    Optional<MaintenanceSchedule> findByIdAndVehicleUser(Long id, User user);

    boolean existsByMaintenanceRecordId(Long maintenanceRecordId);
}