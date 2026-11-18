package calamadevs.controller;

import calamadevs.domain.User;
import calamadevs.dto.Vehicle.*;
import calamadevs.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<ResponseVehicle> postVehicle(
            @RequestBody @Valid PostRequestVehicle dto,
            @AuthenticationPrincipal User user) {

        ResponseVehicle response = vehicleService.postVehicle(dto, user);
        log.info(Thread.currentThread().getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{vehicle-id}")
    public ResponseEntity<ResponseVehicle> getVehicle(
            @AuthenticationPrincipal User user,
            @PathVariable("vehicle-id") Long vehicleId) {

        ResponseVehicle response = vehicleService.getVehicle(vehicleId, user);
        log.info(Thread.currentThread().getName());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<ResponseVehicle>> getVehicles(
            @AuthenticationPrincipal User user,
            @PageableDefault(size = 20) Pageable pageable) {

        Page<ResponseVehicle> page = vehicleService.getListVehiclesByUser(user, pageable);
        log.info(Thread.currentThread().getName());
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @PutMapping()
    public ResponseEntity<PutResponseVehicle> putVehicle(
            @RequestBody @Valid PutRequestVehicle dto,
            @AuthenticationPrincipal User user) {

        PutResponseVehicle response = vehicleService.putVehicle(dto, user);
        log.info(Thread.currentThread().getName());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @DeleteMapping("/{vehicle-id}")
    public ResponseEntity<Void> deleteVehicle(
            @PathVariable("vehicle-id") Long vehicleId,
            @AuthenticationPrincipal User user) {
        vehicleService.deleteVehicle(vehicleId, user);
        log.info(Thread.currentThread().getName());
        return ResponseEntity.noContent().build();
    }

}
