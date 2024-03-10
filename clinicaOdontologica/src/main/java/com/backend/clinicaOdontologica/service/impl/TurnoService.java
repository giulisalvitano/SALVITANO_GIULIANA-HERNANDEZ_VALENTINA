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
        // Registra un mensaje de información para el objeto TurnoEntradaDto recibido
        LOGGER.info("Datos del TurnoEntradaDto recibido: {}", JsonPrinter.toString(turno));

        // Convierte el objeto TurnoEntradaDto en una entidad Turno utilizando el mapper
        Turno turnoEntidad = modelMapper.map(turno, Turno.class);

        // Guarda la entidad del turno en la capa de acceso a datos (DAO) y obtiene la entidad con ID asignado
        Turno turnoEntidadConId = turnoIDao.guardar(turnoEntidad);

        // Convierte la entidad del turno con ID asignado en un objeto TurnoSalidaDto utilizando el mapper
        TurnoSalidaDto turnoSalidaDto = modelMapper.map(turnoEntidadConId, TurnoSalidaDto.class);

        // Registra un mensaje de información para el objeto TurnoSalidaDto generado
        LOGGER.info("Datos del TurnoSalidaDto generado: {}", JsonPrinter.toString(turnoSalidaDto));

        // Retorna el objeto TurnoSalidaDto generado
        return turnoSalidaDto;
    }


    @Override
    public List<TurnoSalidaDto> listarTurnos() {
        // Obtiene una lista de todos los turnos desde la capa DAO y los mapea a objetos TurnoSalidaDto
        List<TurnoSalidaDto> turnosSalidaDto = turnoIDao.listarTodos()
                .stream()
                .map(turno -> modelMapper.map(turno, TurnoSalidaDto.class))
                .collect(Collectors.toList());

        // Registra un mensaje de información para el listado de todos los turnos obtenidos
        LOGGER.info("Listado de todos los turnos: {}", JsonPrinter.toString(turnosSalidaDto));

        // Retorna la lista de objetos TurnoSalidaDto
        return turnosSalidaDto;
    }

    @Override
    public TurnoSalidaDto buscarTurnoPorId(int id) {
        // Busca un turno por su ID en la capa DAO
        Turno turnoBuscado = turnoIDao.buscarPorId(id);

        // Inicializa el objeto TurnoSalidaDto como nulo
        TurnoSalidaDto turnoEncontrado = null;

        // Comprueba si se encontró un turno con el ID especificado
        if (turnoBuscado != null) {
            // Si se encontró, mapea el turno encontrado a un objeto TurnoSalidaDto
            turnoEncontrado = modelMapper.map(turnoBuscado, TurnoSalidaDto.class);

            // Registra un mensaje de información para el turno encontrado
            LOGGER.info("Turno encontrado: {}", JsonPrinter.toString(turnoEncontrado));
        } else {
            // Si no se encontró, registra un mensaje de error
            LOGGER.error("El ID no se encuentra registrado en la base de datos");
        }

        // Retorna el turno encontrado (o nulo si no se encontró)
        return turnoEncontrado;
    }


    private void configureMapping() {
        // Aquí puedes configurar mapeos adicionales si es necesario
    }
}
