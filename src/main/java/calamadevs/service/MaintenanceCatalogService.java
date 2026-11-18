package calamadevs.service;

import calamadevs.domain.Maintenance.MaintenanceItem;
import calamadevs.domain.Maintenance.MaintenanceType;
import calamadevs.dto.Maintenance.MaintenanceCatalog.ResponseMaintenanceCategory;
import calamadevs.dto.Maintenance.MaintenanceCatalog.ResponseMaintenanceItem;
import calamadevs.dto.Maintenance.MaintenanceCatalog.ResponseMaintenanceType;
import calamadevs.repository.Maintenance.MaintenanceCategoryRepository;
import calamadevs.repository.Maintenance.MaintenanceItemRepository;
import calamadevs.repository.Maintenance.MaintenanceTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceCatalogService {

    private final MaintenanceCategoryRepository categoryRepository;
    private final MaintenanceTypeRepository typeRepository;
    private final MaintenanceItemRepository itemRepository;

    public List<ResponseMaintenanceCategory> listCategories() {
        return categoryRepository.findByActiveTrueOrderByNameAsc()
                .stream()
                .map(c -> new ResponseMaintenanceCategory(c.getId(), c.getName()))
                .toList();
    }
    public List<ResponseMaintenanceType> listTypes() {
        return typeRepository.findByActiveTrueOrderByNameAsc()
                .stream()
                .map(this::toTypeDTO)
                .toList();
    }
    public List<ResponseMaintenanceItem> listItemsByCategory(Long categoryId) {
        return itemRepository.findActiveByCategoryWithTypes(categoryId)
                .stream()
                .map(this::toItemDTO)
                .toList();
    }
    public List<ResponseMaintenanceType> listAllowedTypesForItem(Long itemId) {
        return itemRepository.findByIdWithAllowedTypes(itemId)
                .map(item -> item.getAllowedTypes().stream()
                        .filter(MaintenanceType::isActive)
                        .sorted(Comparator.comparing(MaintenanceType::getName))
                        .map(this::toTypeDTO)
                        .toList())
                .orElse(List.of());
    }
    public List<ResponseMaintenanceItem> searchItems(String query) {
        if (query == null || query.isBlank()) {
            return List.of();
        }
        String trimmed = query.trim();
        String normalized = normalize(trimmed);
        return itemRepository.searchByNameOrAlias(trimmed, normalized)
                .stream()
                .map(this::toItemDTO)
                .toList();
    }
    private ResponseMaintenanceItem toItemDTO(MaintenanceItem item) {
        List<ResponseMaintenanceType> types = item.getAllowedTypes().stream()
                .filter(MaintenanceType::isActive)
                .sorted(Comparator.comparing(MaintenanceType::getName))
                .map(this::toTypeDTO)
                .toList();
        return new ResponseMaintenanceItem(
                item.getId(),
                item.getCategory().getId(),
                item.getCategory().getName(),
                item.getName(),
                types
        );
    }
    private ResponseMaintenanceType toTypeDTO(MaintenanceType type) {
        return new ResponseMaintenanceType(type.getId(), type.getCode(), type.getName());
    }
    private String normalize(String input) {
        String noAccents = Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return noAccents.toLowerCase()
                .replace('-', ' ')
                .replaceAll("\\s+", " ")
                .trim();
    }
}