package com.backend.clinicaOdontologica.service;

import com.backend.clinicaOdontologica.dto.entrada.TurnoEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.TurnoSalidaDto;

import java.util.List;

public interface ITurnoService {
    TurnoSalidaDto registrarTurno(TurnoEntradaDto turno);

    List<TurnoSalidaDto> listarTurnos();

    TurnoSalidaDto buscarTurnoPorId(int id);
}
