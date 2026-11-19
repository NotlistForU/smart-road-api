package calamadevs.service;

import calamadevs.domain.Supply.FuelRecord;
import calamadevs.domain.Supply.FuelType;
import calamadevs.domain.Supply.RechargeRecord;
import calamadevs.domain.Supply.SupplyRecord;
import calamadevs.domain.Vehicle.VehicleType;
import calamadevs.domain.Vehicle.Version;
import calamadevs.dto.PublicDashboard.GetRequestDashboardByVersion;
import calamadevs.dto.PublicDashboard.ResponsePublicDashboard;
import calamadevs.repository.Maintenance.MaintenanceRecordRepository;
import calamadevs.repository.Supply.SupplyRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class PublicDashboardService {

    private final SupplyRecordRepository supplyRepository;
    private final SupplyRecordService supplyRecordService;
    private final FuelRecordService fuelRecordService;
    private final RechargeRecordService rechargeRecordService;
    private final VersionService versionService;
    private final MaintenanceRecordRepository maintenanceRecordRepository;

    public ResponsePublicDashboard getSuppliesByVersion(GetRequestDashboardByVersion dto) {

        Version version = versionService.getVersionOrThrow(dto.versionId());
        VehicleType type = version.getVehicleType();

        List<ResponsePublicDashboard.SupplyMetrics> supplyMetrics = new ArrayList<>();

        List<SupplyRecord> supplyList = supplyRepository.findByVersionIdAndPeriod(
                dto.versionId(),
                dto.startDate(),
                dto.endDate());

        Map<Long, FuelRecord> fuelList = new HashMap<>();
        Map<Long, RechargeRecord> rechargeList = new HashMap<>();

        if (!supplyList.isEmpty()) {
            if (type == VehicleType.COMBUSTION || type == VehicleType.PLUGIN_HYBRID) {
                for (FuelRecord f : fuelRecordService.findBySupplyIn(supplyList)) {
                    fuelList.put(f.getSupply().getId(), f);
                }
            }
            if (type == VehicleType.ELECTRIC || type == VehicleType.PLUGIN_HYBRID) {
                for (RechargeRecord r : rechargeRecordService.findBySupplyIn(supplyList)) {
                    rechargeList.put(r.getSupply().getId(), r);
                }
            }
        }

        for (SupplyRecord supply : supplyList) {

            Double km = supply.getKilometersTraveled();

            BigDecimal cost = supplyRecordService.costPerKm(supply.getTotalCost(), km);
            BigDecimal kmPerLiter = null;
            BigDecimal kmPerKwh = null;
            BigDecimal kmPerMj = null;
            BigDecimal key = null;

            FuelRecord fuel = fuelList.get(supply.getId());
            RechargeRecord recharge = rechargeList.get(supply.getId());

            if (type == VehicleType.COMBUSTION) {

                kmPerLiter = supplyRecordService.divide(km, fuel != null ? fuel.getLiters() : null);

            } else if (type == VehicleType.ELECTRIC) {

                kmPerKwh = supplyRecordService.divide(km, recharge != null ? recharge.getKwh() : null);

            } else if (type == VehicleType.PLUGIN_HYBRID) {
                SupplyRecordService.HybridKey hybrid = supplyRecordService.getHybridKey(
                        km,
                        fuel != null ? fuel.getLiters() : null,
                        recharge != null ? recharge.getKwh() : null,
                        fuel != null ? fuel.getFuelType() : null); // <--- repassa o tipo do combustível
                if (hybrid != null) {
                    kmPerMj = hybrid.kmPerMj();
                    key = hybrid.key();
                }
            }
            if (cost == null && kmPerLiter == null && kmPerKwh == null && kmPerMj == null) {
                continue;
            }
            BigDecimal effectiveMjPerLiter = (fuel != null && fuel.getFuelType() != null)
                    ? fuel.getFuelType().getMjPerLiter()
                    : FuelType.GASOLINE.getMjPerLiter();
            supplyMetrics.add(new ResponsePublicDashboard.SupplyMetrics(
                    supply.getSupplyDate() != null ? supply.getSupplyDate().toLocalDate() : null,
                    cost,
                    kmPerLiter,
                    kmPerKwh,
                    kmPerMj,
                    key,
                    effectiveMjPerLiter,
                    SupplyRecordService.MJ_PER_KWH,
                    km,
                    fuel != null ? fuel.getLiters() : null,
                    recharge != null ? recharge.getKwh() : null,
                    fuel != null && fuel.getFuelType() != null ? fuel.getFuelType().name() : null,
                    supply.getTotalCost()));
        }

        LocalDateTime fiveYearsAgo = LocalDateTime.now().minusYears(5);
        List<ResponsePublicDashboard.MaintenanceMetrics> maintenanceMetrics = maintenanceRecordRepository
                .findByVersionIdSince(dto.versionId(), fiveYearsAgo)
                .stream()
                .filter(m -> m.getMaintenanceMileage() != null && m.getMaintenanceMileage() > 0)
                .map(m -> new ResponsePublicDashboard.MaintenanceMetrics(
                        m.getMaintenanceMileage(),
                        m.getTotalCost(),
                        m.isRevision(),
                        m.getItems().stream()
                                .map(ri -> new ResponsePublicDashboard.MaintenanceItemMetrics(
                                        ri.getItem().getName(),
                                        ri.getCost()))
                                .toList()))
                .toList();

        return new ResponsePublicDashboard(supplyMetrics, maintenanceMetrics);
    }

}
