package com.gabriel.smartroad.controller;

import com.gabriel.smartroad.domain.User;
import com.gabriel.smartroad.dto.Trip.RequestDTO;
import com.gabriel.smartroad.dto.Trip.ResponseDTO;
import com.gabriel.smartroad.dto.Trip.UpdateDTO;
import com.gabriel.smartroad.service.Crud.TripService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/trips")
public class TripController {
    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> create(@RequestBody @Valid RequestDTO dto, @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tripService.create(dto, user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> update(@PathVariable Long id, @RequestBody @Valid UpdateDTO dto, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(tripService.update(id, dto, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
        tripService.delete(id, user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/cost")
    public ResponseEntity<Map<String, BigDecimal>> getTripCostReport(@PathVariable Long id, @AuthenticationPrincipal User user) {
        Map<String, BigDecimal> report = tripService.calculateTripCost(id, user);

        return ResponseEntity.ok(report);
    }
}
