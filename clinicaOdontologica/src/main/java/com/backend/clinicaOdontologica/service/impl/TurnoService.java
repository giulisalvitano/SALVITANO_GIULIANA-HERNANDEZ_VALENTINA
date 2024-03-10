package com.backend.clinicaOdontologica.service.impl;

import com.backend.clinicaOdontologica.dao.IDao;
import com.backend.clinicaOdontologica.dto.entrada.TurnoEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.TurnoSalidaDto;
import com.backend.clinicaOdontologica.entity.Turno;
import com.backend.clinicaOdontologica.service.ITurnoService;
import com.backend.clinicaOdontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TurnoService implements ITurnoService {

    private final Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);

    private IDao<Turno> turnoIDao;
    private ModelMapper modelMapper;

    public TurnoService(IDao<Turno> turnoIDao, ModelMapper modelMapper) {
        this.turnoIDao = turnoIDao;
        this.modelMapper = modelMapper;
        configureMapping();
    }

    @Override
    public TurnoSalidaDto registrarTurno(TurnoEntradaDto turno) {
        LOGGER.info("TurnoEntradaDto: {}", JsonPrinter.toString(turno));
        Turno turnoEntidad = modelMapper.map(turno, Turno.class);
        Turno turnoEntidadConId = turnoIDao.guardar(turnoEntidad);
        TurnoSalidaDto turnoSalidaDto = modelMapper.map(turnoEntidadConId, TurnoSalidaDto.class);
        LOGGER.info("TurnoSalidaDto: {}", JsonPrinter.toString(turnoSalidaDto));
        return turnoSalidaDto;
    }

    @Override
    public List<TurnoSalidaDto> listarTurnos() {
        List<TurnoSalidaDto> turnosSalidaDto = turnoIDao.listarTodos()
                .stream()
                .map(turno -> modelMapper.map(turno, TurnoSalidaDto.class))
                .collect(Collectors.toList());
        LOGGER.info("Listado de todos los turnos: {}", JsonPrinter.toString(turnosSalidaDto));
        return turnosSalidaDto;
    }

    @Override
    public TurnoSalidaDto buscarTurnoPorId(int id) {
        Turno turnoBuscado = turnoIDao.buscarPorId(id);
        TurnoSalidaDto turnoEncontrado = null;
        if (turnoBuscado != null) {
            turnoEncontrado = modelMapper.map(turnoBuscado, TurnoSalidaDto.class);
            LOGGER.info("Turno encontrado: {}", JsonPrinter.toString(turnoEncontrado));
        } else {
            LOGGER.error("El ID no se encuentra registrado en la base de datos");
        }
        return turnoEncontrado;
    }

    private void configureMapping() {
        // Aquí puedes configurar mapeos adicionales si es necesario
    }
}
