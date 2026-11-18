package calamadevs.repository.Supply;

import java.util.List;
import java.util.Optional;

import calamadevs.domain.Supply.SupplyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import calamadevs.domain.Supply.RechargeRecord;


@Repository
public interface RechargeRecordRepository extends JpaRepository<RechargeRecord, Long> {
    Optional<RechargeRecord> findBySupply(SupplyRecord supply);

    List<RechargeRecord> findBySupplyIn(List<SupplyRecord> supplies);
}
