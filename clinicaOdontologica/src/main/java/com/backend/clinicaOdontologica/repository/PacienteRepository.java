package com.backend.clinicaOdontologica.repository;

import com.backend.clinicaOdontologica.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByDni(Long dni);
    Paciente findByDniAndNombre(Long dni, String nombre);


}
