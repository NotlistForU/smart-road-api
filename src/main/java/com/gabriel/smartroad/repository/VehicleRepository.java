package com.gabriel.smartroad.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gabriel.smartroad.domain.Vehicle;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    // O Spring já te dá create(), findById(), findAll(), deleteById() de graça
    // aqui.
    // Só precisaremos escrever algo se quisermos uma busca específica no futuro,
    // tipo: buscar todos os carros ativos de um usuário específico.
    Page<Vehicle> findByUserId(Long id, Pageable pageable);

    Optional<Vehicle> findByIdAndUserId(Long id, Long userId);
}
