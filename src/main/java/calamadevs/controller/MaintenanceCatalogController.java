package calamadevs.controller;

import calamadevs.dto.Maintenance.MaintenanceCatalog.ResponseMaintenanceCategory;
import calamadevs.dto.Maintenance.MaintenanceCatalog.ResponseMaintenanceItem;
import calamadevs.dto.Maintenance.MaintenanceCatalog.ResponseMaintenanceType;
import calamadevs.service.MaintenanceCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/catalog/maintenance")
public class MaintenanceCatalogController {

    private final MaintenanceCatalogService catalogService;

    @GetMapping("/categories")
    public ResponseEntity<List<ResponseMaintenanceCategory>> getCategories() {
        return ResponseEntity.ok(catalogService.listCategories());
    }
    @GetMapping("/types")
    public ResponseEntity<List<ResponseMaintenanceType>> getTypes() {
        return ResponseEntity.ok(catalogService.listTypes());
    }
    @GetMapping("/items")
    public ResponseEntity<List<ResponseMaintenanceItem>> getItemsByCategory(
            @RequestParam("category-id") Long categoryId
    ) {
        return ResponseEntity.ok(catalogService.listItemsByCategory(categoryId));
    }
    @GetMapping("/items/{itemId}/allowed-types")
    public ResponseEntity<List<ResponseMaintenanceType>> getAllowedTypes(
            @PathVariable("itemId") Long itemId
    ) {
        return ResponseEntity.ok(catalogService.listAllowedTypesForItem(itemId));
    }
    @GetMapping("/items/search")
    public ResponseEntity<List<ResponseMaintenanceItem>> searchItems(
            @RequestParam("q") String query
    ) {
        return ResponseEntity.ok(catalogService.searchItems(query));
    }
}