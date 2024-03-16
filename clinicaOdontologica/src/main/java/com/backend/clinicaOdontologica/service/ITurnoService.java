package com.backend.clinicaOdontologica.service;

import com.backend.clinicaOdontologica.dto.entrada.TurnoEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.TurnoSalidaDto;
import com.backend.clinicaOdontologica.exception.OdontologoNoEncontradoException;
import com.backend.clinicaOdontologica.exception.PacienteNoEncontradoException;

import java.util.List;

public interface ITurnoService {
    TurnoSalidaDto registrarTurno(TurnoEntradaDto turno);

    List<TurnoSalidaDto> listarTurnos();

    TurnoSalidaDto buscarTurnoPorId(Long id);

    void eliminarTurno(Long id);

    TurnoSalidaDto actualizarTurno(Long id, TurnoEntradaDto turnoDto) throws OdontologoNoEncontradoException, PacienteNoEncontradoException;
}
