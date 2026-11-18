package calamadevs.repository.Maintenance;

import calamadevs.domain.Maintenance.MaintenanceRecord;
import calamadevs.domain.User;
import calamadevs.domain.Vehicle.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MaintenanceRecordRepository extends JpaRepository<MaintenanceRecord, Long> {

  Page<MaintenanceRecord> findByVehicle(Vehicle vehicle, Pageable pageable);

  Optional<MaintenanceRecord> findByIdAndVehicleUser(Long id, User user);

  @Query("""
      SELECT DISTINCT m
      FROM MaintenanceRecord m
      JOIN m.vehicle v
      JOIN v.user u
      JOIN FETCH m.items ri
      JOIN FETCH ri.item i
      JOIN FETCH i.category c
      JOIN FETCH ri.type t
      WHERE v.version.id = :versionId
        AND u.communitySharingEnabled = true
        AND m.maintenanceDate >= :sinceDate
        AND NOT EXISTS (
            SELECT 1 FROM MaintenanceSchedule s
            WHERE s.maintenanceRecord = m
              AND s.status <> calamadevs.domain.Maintenance.MaintenanceScheduleStatus.COMPLETED
        )
      """)
  List<MaintenanceRecord> findByVersionIdSince(
      @Param("versionId") Long versionId,
      @Param("sinceDate") LocalDateTime sinceDate);
}
