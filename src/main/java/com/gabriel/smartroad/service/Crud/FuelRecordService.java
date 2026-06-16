package com.gabriel.smartroad.service.Crud;

import com.gabriel.smartroad.dto.FuelRecord.RequestDTO;
import com.gabriel.smartroad.dto.FuelRecord.ResponseDTO;
import com.gabriel.smartroad.dto.FuelRecord.UpdateDTO;
import com.gabriel.smartroad.exception.VehicleNotFoundException;
import com.gabriel.smartroad.domain.FuelRecord;
import com.gabriel.smartroad.domain.Vehicle;
import com.gabriel.smartroad.domain.User;
import com.gabriel.smartroad.repository.FuelRecordRepository;
import com.gabriel.smartroad.repository.VehicleRepository;
import com.gabriel.smartroad.repository.UserRepository;
import com.gabriel.smartroad.service.Metrics.CalculatorService;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Service
public class FuelRecordService {
    private final FuelRecordRepository fuelRecordRepository;
    private final VehicleRepository vehicleRepository;
    private final CalculatorService calculatorService;
    private final UserRepository userRepository;

    public FuelRecordService(FuelRecordRepository fuelRecordRepository, VehicleRepository vehicleRepository,
            CalculatorService calculatorService, UserRepository userRepository) {
        this.fuelRecordRepository = fuelRecordRepository;
        this.vehicleRepository = vehicleRepository;
        this.calculatorService = calculatorService;
        this.userRepository = userRepository;
    }

    public ResponseDTO create(RequestDTO dto, User user) {
        log.info("Starting fuel supply record for vehicle ID: {}", dto.vehicleId());
        Vehicle vehicle = vehicleRepository.findByIdAndUserId(dto.vehicleId(), user.getId())
                .orElseThrow(() -> new VehicleNotFoundException(dto.vehicleId().toString()));

        FuelRecord record = new FuelRecord();
        record.setVehicle(vehicle);
        record.setRefuelingDate(dto.refuelingDate());
        record.setMileageAtRefueling(dto.mileageAtRefueling());
        record.setLiters(dto.liters());
        record.setPricePerLiter(dto.pricePerLiter());

        BigDecimal discount = BigDecimal.ZERO;
        if(dto.discount() != null) {
             discount = dto.discount();
        }
        BigDecimal subTotal = dto.pricePerLiter().multiply(BigDecimal.valueOf(dto.liters()));
        record.setDiscount(discount);
        record.setTotalCost(subTotal.subtract(discount));


        Double calculatedKml = calculatorService.calculateKmL(vehicle, record);
        record.setKml(calculatedKml);
        if (vehicle.getCurrentMileage() == null || record.getMileageAtRefueling() > vehicle.getCurrentMileage()) {
            vehicle.setCurrentMileage(record.getMileageAtRefueling());
            vehicleRepository.save(vehicle);
        }

        FuelRecord saveRecord = fuelRecordRepository.save(record);

        user.setValidRefuels(user.getValidRefuels() + 1);
        if (user.getValidRefuels() >= 10 && !user.getIsTrustedSource()) {
            log.info("Congratulations! User {} is now a Trusted Source!", user.getId());
            user.setIsTrustedSource(true);
        }
        userRepository.save(user);

        return toResponseDTO(saveRecord);
    }

    public List<ResponseDTO> findByVehicleId(Long vehicleId, User user) {
        // Verifica se o carro realmente existe e (MAIS IMPORTANTE) se ele pertence a
        // quem está logado!
        Vehicle vehicle = vehicleRepository.findByIdAndUserId(vehicleId, user.getId())
                .orElseThrow(() -> new VehicleNotFoundException(vehicleId.toString()));
        return fuelRecordRepository.findByVehicleId(vehicle.getId())
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public ResponseDTO update(Long id, UpdateDTO dto, User user) {
        FuelRecord record = getFuelRecordSafe(id, user);
        if (dto.refuelingDate() != null)
            record.setRefuelingDate(dto.refuelingDate());
        if (dto.mileageAtRefueling() != null)
            record.setMileageAtRefueling(dto.mileageAtRefueling());
        if (dto.liters() != null)
            record.setLiters(dto.liters());
        if (dto.pricePerLiter() != null)
            record.setPricePerLiter(dto.pricePerLiter());
        if (dto.discount() != null) {
            record.setDiscount(dto.discount());
        }

        BigDecimal discount =
                record.getDiscount() != null
                        ? record.getDiscount()
                        : BigDecimal.ZERO;

        BigDecimal subTotal =
                record.getPricePerLiter()
                        .multiply(BigDecimal.valueOf(record.getLiters()));

        record.setTotalCost(subTotal.subtract(discount));

        Double newKml = calculatorService.calculateKmL(record.getVehicle(), record);
        record.setKml(newKml);

        fuelRecordRepository.save(record);
        return toResponseDTO(record);

    }

    public void delete(Long id, User user) {
        FuelRecord record = getFuelRecordSafe(id, user);
        fuelRecordRepository.delete(record);
    }

    public ResponseDTO toResponseDTO(FuelRecord record) {
        return new ResponseDTO(
                record.getId(),
                record.getVehicle().getId(),
                record.getRefuelingDate(),
                record.getMileageAtRefueling(),
                record.getLiters(),
                record.getPricePerLiter(),
                record.getTotalCost(),
                record.getDiscount(),
                record.getKml());
    }

    private FuelRecord getFuelRecordSafe(Long recordId, User user) {
        FuelRecord record = fuelRecordRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("Fuel record not found"));

        if (!record.getVehicle().getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized: You do not own this vehicle!");
        }
        return record;
    }
}
