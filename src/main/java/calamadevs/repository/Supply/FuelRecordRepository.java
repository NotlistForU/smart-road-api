package calamadevs.repository.Supply;

import calamadevs.domain.Supply.FuelRecord;
import calamadevs.domain.Supply.SupplyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository()
public interface FuelRecordRepository extends JpaRepository<FuelRecord, Long> {
    //Just an example how can be -> @Query("SELECT f FROM FuelRecord f INNER JOIN f.supply s WHERE s.id = :id")
    Optional<FuelRecord> findBySupply(SupplyRecord supply);

    List<FuelRecord> findBySupplyIn(List<SupplyRecord> supplies);
}
