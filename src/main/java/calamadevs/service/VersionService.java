package calamadevs.service;

import calamadevs.domain.Vehicle.Version;
import calamadevs.exception.VehicleNotFoundException;
import calamadevs.repository.Vehicle.VersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VersionService {

    private final VersionRepository versionRepository;

    public Version getVersionOrThrow(Long id) {
        return versionRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException("Version not found"));
    }
}
