package calamadevs.service;

import calamadevs.domain.Supply.FuelRecord;
import calamadevs.domain.Supply.FuelType;
import calamadevs.domain.Supply.RechargeRecord;
import calamadevs.domain.Supply.SupplyRecord;
import calamadevs.domain.User;
import calamadevs.domain.Vehicle.Vehicle;
import calamadevs.domain.Vehicle.VehicleType;
import calamadevs.dto.SupplyRecord.PostRequestSupplyRecord;
import calamadevs.dto.SupplyRecord.PutRequestSupplyRecord;
import calamadevs.dto.SupplyRecord.ResponseSupplyRecord;
import calamadevs.exception.SupplyRecordNotFoundException;
import calamadevs.exception.VehicleNotFoundException;
import calamadevs.repository.Supply.SupplyRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SupplyRecordService {
    public static final BigDecimal MJ_PER_KWH = new BigDecimal("3.6");

    private final SupplyRecordRepository supplyRecordRepository;
    private final FuelRecordService fuelRecordService;
    private final RechargeRecordService rechargeRecordService;
    private final VehicleService vehicleService;

    // Utils {
    // ========================================================================================================
    public String autonomy(SupplyRecord supply, FuelRecord fuel, RechargeRecord recharge) {
        double km = supply.getKilometersTraveled();

        if (fuel != null && recharge != null) {
            return String.format(Locale.US, "%.0f km driven using %.1f liters and %.1f kWh",
                    km, fuel.getLiters(), recharge.getKwh());
        }
        if (fuel != null) {
            return String.format(Locale.US, "%.1f km/L", km / fuel.getLiters());
        }
        if (recharge != null) {
            return String.format(Locale.US, "%.1f km/kWh", km / recharge.getKwh());
        }
        throw new IllegalStateException("Supply " + supply.getId() + " has no fuel or recharge data");
    }

    public SupplyRecord getSupplyRecordOrThrow(Long id, User user) {

        try {

            return supplyRecordRepository.findByIdAndVehicleUser(id, user)
                    .orElseThrow(SupplyRecordNotFoundException::new);

        } catch (SupplyRecordNotFoundException e) {

            log.error(e.getMessage());
            throw new SupplyRecordNotFoundException(
                    "Supply record not found with id: " + id
                            + "for the user id: " + user.getId()
                            + "Error message: " + e.getMessage());
        }
    }

    public Page<SupplyRecord> findSuppliesByVehicle(Vehicle vehicle, Pageable pageable) {

        return supplyRecordRepository.findByVehicle(vehicle, pageable);
    }

    public BigDecimal costPerKm(BigDecimal totalCost, Double km) {
        if (totalCost == null || km == null || km <= 0) {
            return null;
        }

        return totalCost.divide(BigDecimal.valueOf(km), 4, RoundingMode.HALF_UP);
    }
    // Utils }
    // ========================================================================================================

    public ResponseSupplyRecord postSupplyRecordRequest(PostRequestSupplyRecord dto, User user) {

        Vehicle vehicle = vehicleService.getVehicleOrThrow(dto.vehicleId(), user.getId());

        FuelRecord fuel = null;
        RechargeRecord recharge = null;

        // COMBUSTION
        if (dto.liters() != null && dto.pricePerLiter() != null) {
            fuel = new FuelRecord();
            fuel.setLiters(dto.liters());
            fuel.setPricePerLiter(dto.pricePerLiter());
            fuel.setFuelType(dto.fuelType() != null ? dto.fuelType() : FuelType.GASOLINE);
        }
        // ELECTRIC
        if (dto.kwh() != null && dto.pricePerKwh() != null) {
            recharge = new RechargeRecord();
            recharge.setKwh(dto.kwh());
            recharge.setPricePerKwh(dto.pricePerKwh());
        }

        SupplyRecord supply = new SupplyRecord();
        supply.setSupplyDate(dto.supplyDate());
        supply.setKilometersTraveled(dto.kilometersTraveled());
        supply.setVehicle(vehicle);
        supply.setTotalCost(totalCost(fuel, recharge));
        supply = this.saveSupplyRecord(supply);

        if (fuel != null) {
            fuel.setSupply(supply);
            fuel = fuelRecordService.saveFuelRecord(fuel);
        }
        if (recharge != null) {
            recharge.setSupply(supply);
            recharge = rechargeRecordService.saveRechargeRecord(recharge);
        }

        if (dto.kilometersTraveled() != null && dto.kilometersTraveled() > 0) {
            double current = vehicle.getMileage() != null ? vehicle.getMileage() : 0.0;
            vehicleService.updateMileage(vehicle, current + dto.kilometersTraveled());
        }

        return supplyRecordToResponse(supply, fuel, recharge);
    }

    public void deleteSupplyRecord(Long supplyId, User user) {

        try {

            SupplyRecord supply = getSupplyRecordOrThrow(supplyId, user);
            supplyRecordRepository.delete(supply);

        } catch (OptimisticLockingFailureException e) {

            log.error(e.getMessage());
            throw new OptimisticLockingFailureException(
                    "Could deleteVehicle supply record with id: " + supplyId
                            + ". Error message: " + e.getMessage());
        }
    }

    public Page<ResponseSupplyRecord> getSupplyRecords(Long vehicleId, User user, Pageable pageable) {
        Vehicle vehicle = vehicleService.getVehicleOrThrow(vehicleId, user.getId());
        Page<SupplyRecord> suppliesPage = findSuppliesByVehicle(vehicle, pageable);
        List<SupplyRecord> listSupplies = suppliesPage.getContent();

        Map<Long, FuelRecord> fuelMap = fuelRecordService.findBySupplyIn(listSupplies)
                .stream()
                .collect(Collectors.toMap(
                        fuel -> fuel.getSupply().getId(),
                        fuel -> fuel));

        Map<Long, RechargeRecord> rechargeMap = rechargeRecordService.findBySupplyIn(listSupplies)
                .stream()
                .collect(Collectors.toMap(
                        recharge -> recharge.getSupply().getId(),
                        recharge -> recharge));

        return suppliesPage.map(supply -> {
            FuelRecord fuel = fuelMap.get(supply.getId());
            RechargeRecord recharge = rechargeMap.get(supply.getId());
            return supplyRecordToResponse(supply, fuel, recharge);
        });
    }

    public ResponseSupplyRecord putSupplyRecord(PutRequestSupplyRecord dto, User user) {

        SupplyRecord supplyRecord = getSupplyRecordOrThrow(dto.id(), user);
        Vehicle vehicle = vehicleService.getVehicleOrThrow(supplyRecord.getVehicle().getId(), user.getId());
        VehicleType vehicleType = vehicleService.getVehicleType(vehicle);

        FuelRecord fuel = null;
        RechargeRecord recharge = null;

        switch (vehicleType) {

            case COMBUSTION -> {

                fuel = fuelRecordService.updateFuelRecord(supplyRecord, dto);
            }
            case ELECTRIC -> {

                recharge = rechargeRecordService.updateRechargeRecord(supplyRecord, dto);
            }
            case PLUGIN_HYBRID -> {

                fuel = fuelRecordService.updateFuelRecord(supplyRecord, dto);
                recharge = rechargeRecordService.updateRechargeRecord(supplyRecord, dto);
            }
            default -> {
                throw new VehicleNotFoundException("Vehicle Type not found: " + vehicleType);
            }
        }

        if (dto.supplyDate() != null) {
            supplyRecord.setSupplyDate(dto.supplyDate());
        }
        if (dto.kilometersTraveled() != null) {
            supplyRecord.setKilometersTraveled(dto.kilometersTraveled());
        }
        supplyRecord.setTotalCost(totalCost(fuel, recharge));

        saveSupplyRecord(supplyRecord);

        return supplyRecordToResponse(supplyRecord, fuel, recharge);
    }

    private SupplyRecord saveSupplyRecord(SupplyRecord supply) {

        try {
            return supplyRecordRepository.save(supply);

        } catch (OptimisticLockingFailureException e) {

            log.error(e.getMessage());
            throw new OptimisticLockingFailureException(
                    "Supply record could not be saved. Error message: " + e.getMessage());

        } catch (IllegalArgumentException e) {

            log.error(e.getMessage());
            throw new IllegalArgumentException(
                    "Supply record could not be saved. Error message: " + e.getMessage());
        }
    }

    // -> total cost uses the database datas.
    private BigDecimal totalCost(FuelRecord fuel, RechargeRecord recharge) {
        BigDecimal totalCost = BigDecimal.ZERO;
        if (fuel != null) {
            BigDecimal pricePerLiter = fuel.getPricePerLiter();
            Double liters = fuel.getLiters();

            totalCost = totalCost.add(
                    pricePerLiter.multiply(BigDecimal.valueOf(liters)));
        }
        if (recharge != null) {
            BigDecimal pricePerKwh = recharge.getPricePerKwh();
            Double kwh = recharge.getKwh();

            totalCost = totalCost.add(
                    pricePerKwh.multiply(BigDecimal.valueOf(kwh)));
        }
        return totalCost.setScale(2, RoundingMode.HALF_UP);
    }

    private ResponseSupplyRecord supplyRecordToResponse(SupplyRecord supply, FuelRecord fuel, RechargeRecord recharge) {
        Double kilometersTraveled = supply.getKilometersTraveled();

        BigDecimal totalCost = totalCost(fuel, recharge);
        BigDecimal costPerKm = costPerKm(totalCost, kilometersTraveled);
        return new ResponseSupplyRecord(
                supply.getId(),
                supply.getVehicle().getId(),
                supply.getSupplyDate(),
                kilometersTraveled,
                fuel != null ? fuel.getFuelType() : null,
                fuel != null ? fuel.getLiters() : null,
                recharge != null ? recharge.getKwh() : null,
                fuel != null ? fuel.getPricePerLiter() : null,
                recharge != null ? recharge.getPricePerKwh() : null,
                totalCost,
                autonomy(supply, fuel, recharge),
                costPerKm);
    }

    public HybridKey getHybridKey(Double km, Double liters, Double kwh, FuelType fuelType) {

        if (km == null || km <= 0)
            return null;

        BigDecimal mjLiters = new BigDecimal("0.0");
        BigDecimal mjKwh = new BigDecimal("0.0");

        if (kwh != null && kwh > 0) {
            mjKwh = MJ_PER_KWH.multiply(BigDecimal.valueOf(kwh));
        }
        if (liters != null && liters > 0) {
            BigDecimal mjRate = (fuelType != null) ? fuelType.getMjPerLiter() : FuelType.GASOLINE.getMjPerLiter();
            mjLiters = mjRate.multiply(BigDecimal.valueOf(liters));
        }

        BigDecimal mjTotal = mjKwh.add(mjLiters);
        if (mjTotal.compareTo(BigDecimal.ZERO) <= 0)
            return null;

        BigDecimal kmPerMj = BigDecimal.valueOf(km).divide(mjTotal, 5, RoundingMode.HALF_UP);

        BigDecimal key = mjLiters.divide(mjTotal, 3, RoundingMode.HALF_UP);
        return new HybridKey(kmPerMj, key);
    }

    public HybridKey getHybridKey(Double km, Double liters, Double kwh) {
        return getHybridKey(km, liters, kwh, FuelType.GASOLINE);
    }

    public BigDecimal divide(Double km, Double supply) {
        if (km == null || supply == null || km <= 0 || supply <= 0) {
            return null;
        }
        return BigDecimal.valueOf(km).divide(BigDecimal.valueOf(supply), 4, RoundingMode.HALF_UP);
    }

    public record HybridKey(BigDecimal kmPerMj, BigDecimal key) {
    }
}
