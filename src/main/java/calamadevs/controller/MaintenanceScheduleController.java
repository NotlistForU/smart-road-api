package calamadevs.controller;

import calamadevs.domain.User;
import calamadevs.dto.Maintenance.MaintenanceSchedule.PostRequestMaintenanceSchedule;
import calamadevs.dto.Maintenance.MaintenanceSchedule.PutRequestMaintenanceSchedule;
import calamadevs.dto.Maintenance.MaintenanceSchedule.ResponseMaintenanceSchedule;
import calamadevs.service.MaintenanceScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/maintenance-schedules")
public class MaintenanceScheduleController {
    private final MaintenanceScheduleService scheduleService;
    @PostMapping
    public ResponseEntity<ResponseMaintenanceSchedule> postSchedule(
            @RequestBody @Valid PostRequestMaintenanceSchedule dto,
            @AuthenticationPrincipal User user) {
        ResponseMaintenanceSchedule response = scheduleService.postSchedule(dto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping
    public ResponseEntity<List<ResponseMaintenanceSchedule>> getSchedulesByVehicle(
            @RequestParam("vehicle-id") Long vehicleId,
            @AuthenticationPrincipal User user) {
        List<ResponseMaintenanceSchedule> response = scheduleService.findSchedulesByVehicle(vehicleId, user);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseMaintenanceSchedule> getScheduleById(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal User user) {
        ResponseMaintenanceSchedule response = scheduleService.getScheduleById(id, user);
        return ResponseEntity.ok(response);
    }
    @PutMapping
    public ResponseEntity<ResponseMaintenanceSchedule> putSchedule(
            @RequestBody @Valid PutRequestMaintenanceSchedule dto,
            @AuthenticationPrincipal User user) {
        ResponseMaintenanceSchedule response = scheduleService.putSchedule(dto, user);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal User user) {
        scheduleService.deleteSchedule(id, user);
        return ResponseEntity.noContent().build();
    }
}