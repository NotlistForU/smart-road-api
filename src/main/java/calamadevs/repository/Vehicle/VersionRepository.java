package calamadevs.repository.Vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import calamadevs.domain.Vehicle.Version;

import java.util.List;

@Repository
public interface VersionRepository extends JpaRepository<Version, Long> {
    List<Version> findByModelIdOrderByNameAsc(Long modelId);
}
