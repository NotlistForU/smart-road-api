package calamadevs.service;

import calamadevs.domain.Supply.FuelRecord;
import calamadevs.domain.Supply.SupplyRecord;
import calamadevs.dto.SupplyRecord.PutRequestSupplyRecord;
import calamadevs.exception.SupplyRecordNotFoundException;
import calamadevs.repository.Supply.FuelRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FuelRecordService {
    private final FuelRecordRepository fuelRecordRepository;

    public List<FuelRecord> findBySupplyIn(List<SupplyRecord> supplies) {

        return fuelRecordRepository.findBySupplyIn(supplies);
    }

    public FuelRecord findBySupply(SupplyRecord supply) {

        try {

            return fuelRecordRepository.findBySupply(supply)
                    .orElseThrow(SupplyRecordNotFoundException::new);

        } catch (SupplyRecordNotFoundException e) {

            log.error(e.getMessage());
            throw new SupplyRecordNotFoundException(
                    "Fuel record not found with supply id: " + supply.getId() + ". Error message: " + e.getMessage()
            );
        }
    }

    public FuelRecord saveFuelRecord(FuelRecord fuelRecord) {

        try {
            return fuelRecordRepository.save(fuelRecord);

        } catch (OptimisticLockingFailureException e) {

            log.error(e.getMessage());
            throw new OptimisticLockingFailureException(
                    "Fuel record could not be saved. Error message: " + e.getMessage()
            );

        } catch (IllegalArgumentException e) {

            log.error(e.getMessage());
            throw new IllegalArgumentException(
                    "Fuel record could not be saved. Error message: " + e.getMessage()
            );
        }
    }

    public void deleteFuelRecord(FuelRecord fuelRecord) {

        try {
            fuelRecordRepository.delete(fuelRecord);

        } catch (OptimisticLockingFailureException e) {

            log.error(e.getMessage());
            throw new OptimisticLockingFailureException(
                    "Fuel record could not be deleted. Error message: " + e.getMessage()
            );

        } catch (IllegalArgumentException e) {

            log.error(e.getMessage());
            throw new IllegalArgumentException(
                    "Fuel record could not be deleted. Error message: " + e.getMessage()
            );
        }
    }


    public FuelRecord updateFuelRecord(SupplyRecord supply, PutRequestSupplyRecord dto) {

        FuelRecord fuelRecord = findBySupply(supply);
        if (dto.liters() != null) {
            fuelRecord.setLiters(dto.liters());
        }
        if (dto.pricePerLiter() != null) {
            fuelRecord.setPricePerLiter(dto.pricePerLiter());
        }
        if (dto.fuelType() != null) {
            fuelRecord.setFuelType(dto.fuelType());
        }
        return saveFuelRecord(fuelRecord);
    }
}
