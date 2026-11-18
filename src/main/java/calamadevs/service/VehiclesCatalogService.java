package calamadevs.service;

import calamadevs.domain.Vehicle.Version;
import calamadevs.dto.VehiclesCatalog.BrandResponseDTO;
import calamadevs.dto.VehiclesCatalog.ModelResponseDTO;
import calamadevs.dto.VehiclesCatalog.VehiclesCatalogResponseDTO;
import calamadevs.dto.VehiclesCatalog.VersionResponseDTO;
import calamadevs.repository.Vehicle.BrandRepository;
import calamadevs.repository.Vehicle.ModelRepository;
import calamadevs.repository.Vehicle.VersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiclesCatalogService {

    private final BrandRepository brandRepository;
    private final ModelRepository modelRepository;
    private final VersionRepository versionRepository;

    public List<BrandResponseDTO> listBrands() {
        return brandRepository.findAllByOrderByNameAsc()
                .stream()
                .map(brand -> new BrandResponseDTO(brand.getId(), brand.getName()))
                .toList();
    }
    public List<ModelResponseDTO> listModelsByBrand(Long brandId) {
        return modelRepository.findByBrandIdOrderByNameAsc(brandId)
                .stream()
                .map(model -> new ModelResponseDTO(model.getId(), model.getName(), model.getModelYear()))
                .toList();
    }
    public List<VersionResponseDTO> listVersionsByModel(Long modelId) {
        return versionRepository.findByModelIdOrderByNameAsc(modelId)
                .stream()
                .map(version -> new VersionResponseDTO(
                        version.getId(),
                        version.getName(),
                        version.getVehicleType(),
                        version.getFuelCategory(),
                        version.getTankCapacity(),
                        version.getBatteryCapacity()
                ))
                .toList();
    }

    public List<VehiclesCatalogResponseDTO> listVehiclesCatalog() {

        List<Version> versions = versionRepository.findAll();
        List<VehiclesCatalogResponseDTO> vehiclesCatalogResponseDTOS = new ArrayList<>();

        for (Version version : versions) {
            VehiclesCatalogResponseDTO vehiclesCatalogResponseDTO = toResponseDTO(version);
            vehiclesCatalogResponseDTOS.add(vehiclesCatalogResponseDTO);
        }

        return vehiclesCatalogResponseDTOS;
    }

    private VehiclesCatalogResponseDTO toResponseDTO(Version version) {

        Long versionId = version.getId();
        String versionName = version.getName();
        String modelName = version.getModel().getName();
        Double tankCapacity = version.getTankCapacity();
        Double batteryCapacity = version.getBatteryCapacity();

        return new VehiclesCatalogResponseDTO(
                versionId,
                versionName,
                modelName,
                tankCapacity,
                batteryCapacity);
    }
}
