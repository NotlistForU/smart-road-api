package calamadevs.service;

import calamadevs.domain.Supply.RechargeRecord;
import calamadevs.domain.Supply.SupplyRecord;
import calamadevs.dto.SupplyRecord.PutRequestSupplyRecord;
import calamadevs.exception.SupplyRecordNotFoundException;
import calamadevs.repository.Supply.RechargeRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RechargeRecordService {

    private final RechargeRecordRepository rechargeRecordRepository;

    public RechargeRecord findBySupply(SupplyRecord supply) {

        try {
            return rechargeRecordRepository.findBySupply(supply)
                    .orElseThrow(SupplyRecordNotFoundException::new);

        } catch (SupplyRecordNotFoundException e) {

            log.error(e.getMessage());
            throw new SupplyRecordNotFoundException(
                    "Recharge Record not found with id: " + supply.getId() + ". Error message: " + e.getMessage()
            );
        }
    }

    public List<RechargeRecord> findBySupplyIn(List<SupplyRecord> supplies) {

        return rechargeRecordRepository.findBySupplyIn(supplies);
    }

    public RechargeRecord saveRechargeRecord(RechargeRecord rechargeRecord) {

        try {
            return rechargeRecordRepository.save(rechargeRecord);

        } catch (OptimisticLockingFailureException e) {

            log.error(e.getMessage());
            throw new OptimisticLockingFailureException(
                    "Recharge record could not be saved. Error message: " + e.getMessage()
            );

        } catch (IllegalArgumentException e) {

            log.error(e.getMessage());
            throw  new IllegalArgumentException(
                    "Recharge record could not be saved. Error message: " + e.getMessage()
            );
        }
    }

    public RechargeRecord updateRechargeRecord(SupplyRecord supply, PutRequestSupplyRecord dto) {

        RechargeRecord rechargeRecord = findBySupply(supply);
        if (dto.kwh() != null) {
            rechargeRecord.setKwh(dto.kwh());
        }
        if (dto.pricePerKwh() != null) {
            rechargeRecord.setPricePerKwh(dto.pricePerKwh());
        }

        return saveRechargeRecord(rechargeRecord);
    }

}
