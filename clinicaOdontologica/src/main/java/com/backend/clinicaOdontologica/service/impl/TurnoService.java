package com.backend.clinicaOdontologica.service.impl;

import com.backend.clinicaOdontologica.dto.entrada.TurnoEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.OdontologoSalidaDto;
import com.backend.clinicaOdontologica.dto.salida.PacienteSalidaDto;
import com.backend.clinicaOdontologica.dto.salida.TurnoSalidaDto;
import com.backend.clinicaOdontologica.entity.Odontologo;
import com.backend.clinicaOdontologica.entity.Paciente;
import com.backend.clinicaOdontologica.entity.Turno;
import com.backend.clinicaOdontologica.exception.BadRequestException;
import com.backend.clinicaOdontologica.exception.OdontologoNoEncontradoException;
import com.backend.clinicaOdontologica.exception.PacienteNoEncontradoException;
import com.backend.clinicaOdontologica.service.ITurnoService;
import com.backend.clinicaOdontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
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
        this.pacienteRepository = pacienteRepository;
        this.odontologoRepository = odontologoRepository;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }


/*
    @Override
    public TurnoSalidaDto registrarTurno(TurnoEntradaDto turno) {
        LOGGER.info("Datos del TurnoEntradaDto recibido: {}", JsonPrinter.toString(turno));

        Turno turnoEntidad = turnoRepository.save(turno, Turno.class);

        Turno turnoEntidadConId = turnoIDao.guardar(turnoEntidad);

        TurnoSalidaDto turnoSalidaDto = modelMapper.map(turnoEntidadConId, TurnoSalidaDto.class);

        LOGGER.info("Datos del TurnoSalidaDto generado: {}", JsonPrinter.toString(turnoSalidaDto));

        return turnoSalidaDto;
    }
*/
/*
    @Override
    public TurnoSalidaDto registrarTurno(TurnoEntradaDto turno) throws OdontologoNoEncontradoException, PacienteNoEncontradoException {
        LOGGER.info("Datos del TurnoEntradaDto recibido: {}", JsonPrinter.toString(turno));

        // Busca el paciente en base al ID proporcionado en el TurnoEntradaDto
        Paciente paciente = pacienteRepository.findById(turno.getIdPaciente())
                .orElseThrow(() -> new PacienteNoEncontradoException("No se encontró el paciente para agendar el turno con id: " + turno.getIdPaciente()));

        // Busca el odontólogo en base al ID proporcionado en el TurnoEntradaDto
        Odontologo odontologo = odontologoRepository.findById(turno.getIdOdontologo())
                .orElseThrow(() -> new OdontologoNoEncontradoException("No se encontró el odontólogo para agendar el turno con id: " + turno.getIdOdontologo()));

        LOGGER.info("Registrando turno para el paciente: {} y el odontólogo: {}", paciente, odontologo);

        // Crea una instancia de Turno a partir de los datos proporcionados en el TurnoEntradaDto
        Turno turnoEntidad = new Turno(odontologo, paciente, turno.getFechaYHora());

        // Guarda el turno en la base de datos
        Turno turnoEntidadConId = turnoRepository.save(turnoEntidad);

        // Mapea la entidad del turno guardado a un objeto TurnoSalidaDto
        TurnoSalidaDto turnoSalidaDto = modelMapper.map(turnoEntidadConId, TurnoSalidaDto.class);

        LOGGER.info("Datos del TurnoSalidaDto generado: {}", JsonPrinter.toString(turnoSalidaDto));

        return turnoSalidaDto;
    }
*/
    @Override
    public TurnoSalidaDto registratTurno(TurnoEntradaDto turnoEntradaDto) throws BadRequestException {
        TurnoSalidaDto turnoSalidaDto;
        PacienteSalidaDto paciente = pacienteService.buscarPacientePorId(turnoEntradaDto.getIdPaciente());
        OdontologoSalidaDto odontologo = odontologoService.buscarOdontologoPorId(turnoEntradaDto.getIdOdontologo());

        String pacienteNoEnBDD = "El paciente no se encuentra en la base datos";
        String odontologoNoEnBDD = "El odontologo no se encuentra en la base datos";
        String ambosNulos = "El paciente y el odontologo no se encuentran en la base de datos";


        if (paciente == null || odontologo == null) {
            if (paciente == null && odontologo == null) {
                LOGGER.error(ambosNulos);
                throw new BadRequestException(ambosNulos);
            }else if (paciente == null){
                LOGGER.error(pacienteNoEnBDD);
                throw new BadRequestException(pacienteNoEnBDD);
            }else LOGGER.error(odontologoNoEnBDD);
                throw new BadRequestException(odontologoNoEnBDD);

        } else {
            Turno turnoNuevo = turnoRepository.save(modelMapper.map(turnoEntradaDto, Turno.class));
            turnoSalidaDto = entidadADtoSalida(turnoNuevo, paciente, odontologo);
            LOGGER.info("Nuevo turno registrado con exito {}", turnoSalidaDto);
        }

        return turnoSalidaDto;
    }

    @Override
    public TurnoSalidaDto registrarTurno(TurnoEntradaDto turno) {
        return null;
    }

    /*



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
      */
    @Override
    public List<TurnoSalidaDto> listarTurnos() {
        List<TurnoSalidaDto> turnosSalidaDto = turnoRepository.findAll()
                .stream()
                .map(turno -> modelMapper.map(turno, TurnoSalidaDto.class))
                .collect(Collectors.toList());

        LOGGER.info("Listado de todos los turnos: {}", JsonPrinter.toString(turnosSalidaDto));

        return turnosSalidaDto;
    }

  /*
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
    }*/

    @Override
    public TurnoSalidaDto buscarTurnoPorId(Long id) {
        Turno turnoBuscado = turnoRepository.findById(id).orElse(null);
        TurnoSalidaDto turnoEncontrado = null;

        if (turnoBuscado != null) {
            turnoEncontrado = modelMapper.map(turnoBuscado, TurnoSalidaDto.class);
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
    public TurnoSalidaDto actualizarTurno(Long id, TurnoEntradaDto turnoDto) throws OdontologoNoEncontradoException, PacienteNoEncontradoException {
        Turno turno = turnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado con el ID proporcionado: " + id));

        // Actualizamos los campos del turno con los datos proporcionados en turnoDto
        Paciente paciente = pacienteRepository.findById(turnoDto.getIdPaciente())
                .orElseThrow(() -> new PacienteNoEncontradoException("No se encontró el paciente con el ID: " + turnoDto.getIdPaciente()));
        turno.setPaciente(paciente);

        Odontologo odontologo = odontologoRepository.findById(turnoDto.getIdOdontologo())
                .orElseThrow(() -> new OdontologoNoEncontradoException("No se encontró el odontólogo con el ID: " + turnoDto.getIdOdontologo()));
        turno.setOdontologo(odontologo);

        turno.setFechaYHora(turnoDto.getFechaYHora());

        // Guardamos el turno actualizado en el repositorio
        Turno turnoActualizado = turnoRepository.save(turno);

        LOGGER.info("Turno actualizado con éxito: {}", JsonPrinter.toString(turnoActualizado));

        // Retornamos el objeto TurnoSalidaDto correspondiente al turno actualizado
        return modelMapper.map(turnoActualizado, TurnoSalidaDto.class);
    }

    private TurnoSalidaDto entidadADtoSalida(Turno turno, PacienteSalidaDto pacienteSalidaDto, OdontologoSalidaDto odontologoSalidaDto){
        TurnoSalidaDto turnoSalidaDto =modelMapper.map(turno, TurnoSalidaDto.class);
        turnoSalidaDto.setPacienteSalidaDto(pacienteSalidaDto);
        turnoSalidaDto.setOdontologoSalidaDto(odontologoSalidaDto);


        return turnoSalidaDto;
    }


    private void configureMapping() {
        // Aquí puedes configurar mapeos adicionales si es necesario
    }

}




