package com.gabriel.smartroad.controller;

import com.gabriel.smartroad.service.Metrics.StaticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class PublicDashboardController {
    private final StaticsService staticsService;

    public PublicDashboardController(StaticsService staticsService) {
        this.staticsService = staticsService;
    }

    @GetMapping("/statistics/{model}/{version}")
    public ResponseEntity<Double> getVehicleAverageConsumption(@PathVariable String model, @PathVariable String version){
        Double averageKml = staticsService.getAverageKmlForModelAndVersion(model, version);

        return ResponseEntity.ok(averageKml);
    }
}
