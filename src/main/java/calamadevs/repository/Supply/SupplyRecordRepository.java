package calamadevs.repository.Supply;

import calamadevs.domain.Supply.SupplyRecord;
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
public interface SupplyRecordRepository extends JpaRepository<SupplyRecord, Long> {
  Page<SupplyRecord> findByVehicle(Vehicle vehicle, Pageable pageable);

  Optional<SupplyRecord> findByIdAndVehicleUser(Long id, User user);

  @Query("""
          SELECT s
          FROM SupplyRecord s
          JOIN s.vehicle v
          JOIN v.user u
          WHERE v.version.id = :versionId
            AND u.communitySharingEnabled = true
            AND s.supplyDate >= :startDate
            AND s.supplyDate < :endDate
      """)
  List<SupplyRecord> findByVersionIdAndPeriod(
      @Param("versionId") Long versionId,
      @Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate);
}
