package com.backend.clinicaOdontologica.service.impl;

import com.backend.clinicaOdontologica.dto.entrada.PacienteEntradaDto;
import com.backend.clinicaOdontologica.dto.entrada.TurnoEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.OdontologoSalidaDto;
import com.backend.clinicaOdontologica.dto.salida.PacienteSalidaDto;
import com.backend.clinicaOdontologica.dto.salida.TurnoSalidaDto;
import com.backend.clinicaOdontologica.entity.Odontologo;
import com.backend.clinicaOdontologica.entity.Paciente;
import com.backend.clinicaOdontologica.entity.Turno;
import com.backend.clinicaOdontologica.exception.BadRequestException;
import com.backend.clinicaOdontologica.exception.ResourceNotFoundException;
import com.backend.clinicaOdontologica.service.ITurnoService;
import com.backend.clinicaOdontologica.utils.JsonPrinter;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.backend.clinicaOdontologica.repository.PacienteRepository;
import com.backend.clinicaOdontologica.repository.OdontologoRepository;
import com.backend.clinicaOdontologica.repository.TurnoRepository;


@Service
public class TurnoService implements ITurnoService {
    private final Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);

    private final ModelMapper modelMapper;

    private final TurnoRepository turnoRepository;
    private final PacienteRepository pacienteRepository;
    private final OdontologoRepository odontologoRepository;
    private final PacienteService pacienteService;
    private final OdontologoService odontologoService;


    public TurnoService(ModelMapper modelMapper, TurnoRepository turnoRepository, PacienteRepository pacienteRepository, OdontologoRepository odontologoRepository, PacienteService pacienteService, OdontologoService odontologoService) {
        this.modelMapper = modelMapper;
        this.turnoRepository = turnoRepository;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
        this.pacienteRepository = pacienteRepository;
        this.odontologoRepository = odontologoRepository;
        configureMapping();
    }


    @Override
    public TurnoSalidaDto registrarTurno(TurnoEntradaDto turnoEntradaDto) throws BadRequestException {


        TurnoSalidaDto turnoSalidaDto;
        OdontologoSalidaDto odontologo = odontologoService.buscarOdontologoPorId(turnoEntradaDto.getIdOdontologo());

        PacienteSalidaDto paciente = pacienteService.buscarPacientePorId(turnoEntradaDto.getIdPaciente());

        String pacienteNoEnBdd = "El paciente no se encuentra en nuestra base de datos";
        String odontologoNoEnBdd = "El odontologo no se encuentra en nuestra base de datos";
        String ambosNulos = "El paciente y el odontologo no se encuentran en nuestra base de datos";



        if (paciente == null || odontologo == null) {
            if (paciente == null && odontologo == null) {
                LOGGER.error(ambosNulos);
                throw new BadRequestException(ambosNulos);
            } else if (paciente == null) {
                LOGGER.error(pacienteNoEnBdd);
                throw new BadRequestException(pacienteNoEnBdd);
            } else {
                LOGGER.error(odontologoNoEnBdd);
                throw new BadRequestException(odontologoNoEnBdd);
            }


        } else {
            // Crear un nuevo turno y asignar el paciente y el odontólogo
            Turno turnoNuevo = modelMapper.map(turnoEntradaDto, Turno.class);
            turnoNuevo.setPaciente(modelMapper.map(paciente, Paciente.class));
            turnoNuevo.setOdontologo(modelMapper.map(odontologo, Odontologo.class));

            // Guardar el turno en la base de datos
            Turno turnoGuardado = turnoRepository.save(turnoNuevo);
            turnoSalidaDto = entidadADtoSalida(turnoGuardado);
            LOGGER.info("Nuevo turno registrado con éxito: {}", turnoSalidaDto);
        }



        return turnoSalidaDto;
    }



    @Override
    public List<TurnoSalidaDto> listarTurnos() {
        List<TurnoSalidaDto> turnosSalidaDto = turnoRepository.findAll()
                .stream()
                .map(this::entidadADtoSalida)
                .toList();

        LOGGER.info("Listado de todos los turnos: {}", JsonPrinter.toString(turnosSalidaDto));
        return turnosSalidaDto;
    }



    @Override
    public TurnoSalidaDto buscarTurnoPorId(Long id) {
        Turno turnoBuscado = turnoRepository.findById(id).orElse(null);
        TurnoSalidaDto turnoEncontrado = null;

        if (turnoBuscado != null) {
            turnoEncontrado = entidadADtoSalida(turnoBuscado);
            LOGGER.info("Turno encontrado: {}", JsonPrinter.toString(turnoEncontrado));
        } else {
            LOGGER.error("El ID no se encuentra registrado en la base de datos");
        }

        return turnoEncontrado;
    }


    @Override
    public void eliminarTurno(Long id) {
        Turno turno = turnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado con el ID proporcionado: " + id));

        turnoRepository.delete(turno);
        LOGGER.info("Turno eliminado con éxito: {}", JsonPrinter.toString(turno));
    }


    @Override
    public TurnoSalidaDto modificarTurno(Long id, TurnoEntradaDto turnoEntradaDto) throws ResourceNotFoundException {
        // Buscar el turno por su ID
        Turno turno = turnoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el turno a actualizar con id: " + id));

        // Obtener el odontólogo y el paciente correspondientes a los IDs proporcionados en el DTO de entrada
        OdontologoSalidaDto odontologo = odontologoService.buscarOdontologoPorId(turnoEntradaDto.getIdOdontologo());
        PacienteSalidaDto paciente = pacienteService.buscarPacientePorId(turnoEntradaDto.getIdPaciente());

        // Mapear los datos del DTO de entrada al turno existente
        turno.setOdontologo(modelMapper.map(odontologo, Odontologo.class));
        turno.setPaciente(modelMapper.map(paciente, Paciente.class));
        turno.setFechaYHora(turnoEntradaDto.getFechaYHora());

        // Guardar el turno actualizado en la base de datos y mapear el resultado al DTO de salida
        Turno turnoActualizado = turnoRepository.save(turno);
        return modelMapper.map(turnoActualizado, TurnoSalidaDto.class);
    }




    private PacienteSalidaDto pacienteSalidaDtoASalidaTurnoDto(Long id){
        return pacienteService.buscarPacientePorId(id);
    }

    private OdontologoSalidaDto odontologoSalidaDtoASalidaTurnoDto(Long id){
        return odontologoService.buscarOdontologoPorId(id);
    }


    private TurnoSalidaDto entidadADtoSalida(Turno turno) {
        TurnoSalidaDto turnoSalidaDto = modelMapper.map(turno, TurnoSalidaDto.class);
        turnoSalidaDto.setPacienteSalidaDto(pacienteSalidaDtoASalidaTurnoDto(turno.getPaciente().getId()));
        turnoSalidaDto.setOdontologoSalidaDto(odontologoSalidaDtoASalidaTurnoDto(turno.getOdontologo().getId()));

        return turnoSalidaDto;
    }



    private void configureMapping() {
        modelMapper.getConfiguration()
                .setPropertyCondition(Conditions.isNotNull())
                .setMatchingStrategy(MatchingStrategies.STRICT);

        // Mapeo de TurnoEntradaDto a Turno
        modelMapper.createTypeMap(TurnoEntradaDto.class, Turno.class)
                .addMappings(mapping -> {
                    mapping.map(TurnoEntradaDto::getIdPaciente, Turno::setPaciente);
                    mapping.map(TurnoEntradaDto::getIdOdontologo, Turno::setOdontologo);
                });

        // Mapeo de Turno a TurnoSalidaDto
        modelMapper.createTypeMap(Turno.class, TurnoSalidaDto.class)
                .addMapping(Turno::getId, TurnoSalidaDto::setId);

    }





}






