package com.gabriel.smartroad.catalago;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/catalog")
public class VehicleCatalogController {
    private final VehicleCatalogService vehicleCatalogService;

    public VehicleCatalogController(VehicleCatalogService vehicleCatalogService) {
        this.vehicleCatalogService = vehicleCatalogService;
    }

    @GetMapping("/brands")
    public ResponseEntity<List<String>> getBrands() {
        return ResponseEntity.ok(vehicleCatalogService.getBrands());
    }

    @GetMapping("/models")
    public ResponseEntity<List<String>> getModels(@RequestParam String brand) {
        List<String> models = vehicleCatalogService.getModels(brand);
        if (models.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(models);
    }

    @GetMapping("/versions")
    public ResponseEntity<List<String>> getVersions(@RequestParam String brand, @RequestParam String model) {
        List<String> versions = vehicleCatalogService.getVersions(brand, model);
        if (versions.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(versions);
    }

    @GetMapping("/years")
    public ResponseEntity<List<Integer>> getYears(@RequestParam String brand, @RequestParam String model, @RequestParam String version) {
        List<Integer> years = vehicleCatalogService.getYears(brand, model, version);
        if (years.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(years);
    }

    @GetMapping("/tank-capacity")
    public ResponseEntity<Integer> getTankCapacity(@RequestParam String brand, @RequestParam String model, @RequestParam String version, @RequestParam int year) {
        Integer tankCapacity = vehicleCatalogService.getTankCapacity(brand, model, version, year);
        if (tankCapacity == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(tankCapacity);
    }
}
