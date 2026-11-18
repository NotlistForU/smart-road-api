package calamadevs.service;

import calamadevs.domain.User;
import calamadevs.domain.Vehicle.Vehicle;
import calamadevs.domain.Vehicle.VehicleType;
import calamadevs.domain.Vehicle.Version;
import calamadevs.dto.Vehicle.PostRequestVehicle;
import calamadevs.dto.Vehicle.PutRequestVehicle;
import calamadevs.dto.Vehicle.PutResponseVehicle;
import calamadevs.dto.Vehicle.ResponseVehicle;
import calamadevs.exception.VehicleNotFoundException;
import calamadevs.repository.Vehicle.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VersionService versionService;

    public ResponseVehicle getVehicle(Long vehicleId, User user) {
        return toResponse(getVehicleOrThrow(vehicleId, user.getId()));
    }

    public Vehicle getVehicleOrThrow(Long id, Long userId) {

        try {
            return vehicleRepository.findByIdAndUserIdAndActiveTrue(id, userId)
                    .orElseThrow(VehicleNotFoundException::new);

        } catch (VehicleNotFoundException e) {

            log.error(e.getMessage());
            throw new VehicleNotFoundException(
                    "Vehicle not found for the user"
                            + e.getMessage());
        }
    }

    public ResponseVehicle postVehicle(PostRequestVehicle dto, User user) {

        if (dto.versionId() == null) {
            throw new VehicleNotFoundException("Version id not found");
        }

        Vehicle vehicle = new Vehicle();
        Version version = versionService.getVersionOrThrow(dto.versionId());
        vehicle.setVersion(version);
        vehicle.setUser(user);
        vehicle.setActive(true);
        vehicle.setNickName(dto.vehicleNickName());
        vehicle.setMileage(dto.mileage() != null ? dto.mileage() : 0.0);

        return toResponse(saveVehicle(vehicle));
    }

    public Page<ResponseVehicle> getListVehiclesByUser(User user, Pageable pageable) {

        try {

            Page<Vehicle> vehicles = vehicleRepository.findByUserIdAndActiveTrue(user.getId(), pageable);

            return vehicles.map(this::toResponse);

        } catch (VehicleNotFoundException e) {

            log.error(e.getMessage());
            throw new VehicleNotFoundException(
                    "No Vehicle found for the user. Error: " + e.getMessage());
        }
    }

    public PutResponseVehicle putVehicle(PutRequestVehicle dto, User user) {
        Vehicle vehicle = update(dto, user);
        return toPutResponse(vehicle);
    }

    // SOFT DELETE
    public void deleteVehicle(Long vehicleId, User user) {
        Vehicle vehicle = getVehicleOrThrow(vehicleId, user.getId());
        vehicle.setActive(false);
        saveVehicle(vehicle);
    }

    public VehicleType getVehicleType(Vehicle vehicle) {
        return vehicle.getVersion().getVehicleType();
    }

    public void updateMileage(Vehicle vehicle, Double newMileage) {
        if (newMileage == null)
            return;
        vehicle.setMileage(newMileage);
        saveVehicle(vehicle);
    }

    private Vehicle saveVehicle(Vehicle vehicle) {

        try {
            return vehicleRepository.save(vehicle);

        } catch (OptimisticLockingFailureException e) {

            log.error(e.getMessage());
            throw new OptimisticLockingFailureException(
                    "Vehicle could not be saved. Error message: " + e.getMessage());
        }
    }

    private Vehicle update(PutRequestVehicle dto, User user) {

        Vehicle vehicle = getVehicleOrThrow(dto.vehicleId(), user.getId());
        vehicle.setNickName(dto.vehicleNickName());
        if (dto.mileage() != null) {
            vehicle.setMileage(dto.mileage());
        }
        return saveVehicle(vehicle);
    }

    private ResponseVehicle toResponse(Vehicle vehicle) {
        Version version = vehicle.getVersion();
        return new ResponseVehicle(
                vehicle.getId(),
                version.getId(),
                vehicle.getNickName(),
                version.getName(),
                version.getModel().getBrand().getName(),
                version.getModel().getName(),
                version.getModel().getModelYear(),
                version.getVehicleType(),
                version.getFuelCategory(),
                version.getTankCapacity(),
                version.getBatteryCapacity(),
                vehicle.getCreatedAt(),
                vehicle.getMileage());
    }

    private PutResponseVehicle toPutResponse(Vehicle vehicle) {
        return new PutResponseVehicle(
                vehicle.getId(), vehicle.getVersion().getId(), vehicle.getNickName(), vehicle.getUpdatedAt());
    }
}