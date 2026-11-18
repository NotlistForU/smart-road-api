package calamadevs.controller;

import calamadevs.domain.User;
import calamadevs.dto.Maintenance.MaintenanceRecord.PostRequestMaintenanceRecord;
import calamadevs.dto.Maintenance.MaintenanceRecord.PutRequestMaintenanceRecord;
import calamadevs.dto.Maintenance.MaintenanceRecord.ResponseMaintenanceRecord;
import calamadevs.service.MaintenanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/maintenances")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    @PostMapping
    public ResponseEntity<ResponseMaintenanceRecord> postMaintenance(
            @RequestBody @Valid PostRequestMaintenanceRecord dto,
            @AuthenticationPrincipal User user) {
        ResponseMaintenanceRecord response = maintenanceService.postMaintenance(dto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<ResponseMaintenanceRecord>> getMaintenances(
            @RequestParam("vehicle-id") Long vehicleId,
            @AuthenticationPrincipal User user,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<ResponseMaintenanceRecord> response = maintenanceService.findMaintenancesByVehicle(vehicleId, user, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMaintenanceRecord> getMaintenanceById(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal User user) {
        ResponseMaintenanceRecord response = maintenanceService.getMaintenanceById(id, user);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<ResponseMaintenanceRecord> putMaintenance(
            @RequestBody @Valid PutRequestMaintenanceRecord dto,
            @AuthenticationPrincipal User user) {
        ResponseMaintenanceRecord response = maintenanceService.putMaintenance(dto, user);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaintenance(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal User user) {
        maintenanceService.deleteMaintenance(id, user);
        return ResponseEntity.noContent().build();
    }
}