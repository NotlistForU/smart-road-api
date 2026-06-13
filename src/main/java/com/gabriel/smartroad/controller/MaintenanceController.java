package com.gabriel.smartroad.controller;


import com.gabriel.smartroad.domain.User;
import com.gabriel.smartroad.dto.Maintenance.RequestDTO;
import com.gabriel.smartroad.dto.Maintenance.ResponseDTO;
import com.gabriel.smartroad.dto.Maintenance.UpdateDTO;
import com.gabriel.smartroad.service.Crud.MaintenanceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenances")
public class MaintenanceController {
    private final MaintenanceService maintenanceService;

    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> create(@RequestBody @Valid RequestDTO dto, @AuthenticationPrincipal User user) {
        ResponseDTO response = maintenanceService.create(dto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<ResponseDTO>> findByVehicleId(@PathVariable Long vehicleId, @AuthenticationPrincipal User user) {
        List<ResponseDTO> response = maintenanceService.findByVehicleId(vehicleId, user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> update(@PathVariable Long id, @RequestBody @Valid UpdateDTO dto, @AuthenticationPrincipal User user) {
        ResponseDTO response = maintenanceService.update(id, dto, user);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
        maintenanceService.delete(id, user);
        return ResponseEntity.noContent().build();
    }


}
