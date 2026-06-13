package com.gabriel.smartroad.controller;

import com.gabriel.smartroad.dto.FuelRecord.UpdateDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.http.HttpStatus;

import jakarta.validation.Valid;

import com.gabriel.smartroad.dto.FuelRecord.RequestDTO;
import com.gabriel.smartroad.dto.FuelRecord.ResponseDTO;
import com.gabriel.smartroad.service.Crud.FuelRecordService;
import com.gabriel.smartroad.domain.User;

import java.util.List;

@RestController
@RequestMapping("/api/fuel-records")
public class FuelRecordController {
    private final FuelRecordService fuelRecordService;

    public FuelRecordController(FuelRecordService fuelRecordService) {
        this.fuelRecordService = fuelRecordService;
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> create(@RequestBody @Valid RequestDTO dto, @AuthenticationPrincipal User user) {
        ResponseDTO response = fuelRecordService.create(dto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<ResponseDTO>> findByVehicleId(@PathVariable Long vehicleId,
            @AuthenticationPrincipal User user) {
        List<ResponseDTO> response = fuelRecordService.findByVehicleId(vehicleId, user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> update(@PathVariable Long id, @RequestBody @Valid UpdateDTO dto, @AuthenticationPrincipal User user) {
        ResponseDTO response =  fuelRecordService.update(id, dto, user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal User user){
        fuelRecordService.delete(id, user);
        return ResponseEntity.noContent().build();
    }

}
