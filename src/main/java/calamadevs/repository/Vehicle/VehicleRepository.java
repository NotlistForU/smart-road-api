package calamadevs.repository.Vehicle;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import calamadevs.domain.Vehicle.Vehicle;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    // O Spring já te dá supplyRecharge(), findById(), findAll(), deleteById() de
    // graça
    // aqui.
    // Só precisaremos escrever algo se quisermos uma busca específica no futuro,
    // tipo: buscar todos os carros ativos de um usuário específico.
    Page<Vehicle> findByUserIdAndActiveTrue(Long id, Pageable pageable);

    Optional<Vehicle> findByIdAndUserIdAndActiveTrue(Long id, Long userId);
}
