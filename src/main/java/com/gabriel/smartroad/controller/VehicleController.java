package com.gabriel.smartroad.controller;

import com.gabriel.smartroad.domain.User;
import com.gabriel.smartroad.dto.Vehicle.FinancialDashboardDTO;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.gabriel.smartroad.dto.Vehicle.ResponseDTO;
import com.gabriel.smartroad.dto.Vehicle.RequestDTO;
import com.gabriel.smartroad.dto.Vehicle.UpdateDTO;
import com.gabriel.smartroad.service.Crud.VehicleService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    // 1. ENDPOINT de criar (POST)
    @PostMapping
    public ResponseEntity<ResponseDTO> create(@RequestBody @Valid RequestDTO dto, @AuthenticationPrincipal User user) {
        System.out.println(dto);
        ResponseDTO response = vehicleService.create(dto, user);
        // Retorna o status 201 (Created) que é o padrão correto da web para quando algo
        // é salvo
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 2. ENDPOINT de buscar por ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> findById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        ResponseDTO response = vehicleService.findById(id, user);
        return ResponseEntity.ok(response);
    }

    // 2.2 ENDPOINT de dashboard financeiro (GET)
    @GetMapping("/{id}/financial-dashboard")
    public ResponseEntity<FinancialDashboardDTO> getFinancialDashboard(
            @PathVariable Long id,
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate

    ) {
        FinancialDashboardDTO dashboard = vehicleService.getFinancialDashboard(id, user, startDate, endDate);
        return ResponseEntity.ok(dashboard);
    }

    // 3. ENDPOINT de buscar todos por USER (GET)
    @GetMapping
    public ResponseEntity<Page<ResponseDTO>> listAllByUser(@AuthenticationPrincipal User user,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<ResponseDTO> page = vehicleService.findAllByUser(user, pageable);
        return ResponseEntity.ok(page);
    }

    // 3.2 ENDPOINT de buscar todos. (GET)
    @GetMapping("/public")
    public ResponseEntity<Page<ResponseDTO>> listAll(@PageableDefault(size = 20) Pageable pageable) {
        Page<ResponseDTO> page = vehicleService.findAll(pageable);
        return ResponseEntity.ok(page); // Retorna status 200 (OK)
    }

    // 4. ENDPOINT de atualizar (PUT ou PATCH)
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> update(@PathVariable Long id, @RequestBody @Valid UpdateDTO dto,
            @AuthenticationPrincipal User user) {
        ResponseDTO response = vehicleService.update(id, dto, user);
        return ResponseEntity.ok(response);
    }

    // 5. ENDPOINT de deletar ()
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
        vehicleService.delete(id, user);
        // Retorna status 204 (No Content)
        // "Ação concluída com sucesso e não há nada para te devolver na tela"
        return ResponseEntity.noContent().build();
    }
}
