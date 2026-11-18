package calamadevs.controller;

import calamadevs.dto.VehiclesCatalog.BrandResponseDTO;
import calamadevs.dto.VehiclesCatalog.ModelResponseDTO;
import calamadevs.dto.VehiclesCatalog.VehiclesCatalogResponseDTO;
import calamadevs.dto.VehiclesCatalog.VersionResponseDTO;
import calamadevs.service.VehiclesCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public")
public class VehiclesCatalogController {

    private final VehiclesCatalogService vehiclesCatalogService;

    @GetMapping("/vehicles-catalog")
    public ResponseEntity<List<VehiclesCatalogResponseDTO>> getVehiclesCatalog() {
        List<VehiclesCatalogResponseDTO> list = vehiclesCatalogService.listVehiclesCatalog();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/catalog/brands")
    public ResponseEntity<List<BrandResponseDTO>> getBrands() {
        return ResponseEntity.ok(vehiclesCatalogService.listBrands());
    }

    @GetMapping("/catalog/models")
    public ResponseEntity<List<ModelResponseDTO>> getModels(
            @RequestParam("brand-id") Long brandId
    ) {
        return ResponseEntity.ok(vehiclesCatalogService.listModelsByBrand(brandId));
    }

    @GetMapping("/catalog/versions")
    public ResponseEntity<List<VersionResponseDTO>> getVersions(
            @RequestParam("model-id") Long modelId
    ) {
        return ResponseEntity.ok(vehiclesCatalogService.listVersionsByModel(modelId));
    }
}
