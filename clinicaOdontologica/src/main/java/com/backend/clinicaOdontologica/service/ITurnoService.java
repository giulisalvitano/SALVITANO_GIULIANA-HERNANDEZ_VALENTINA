package com.backend.clinicaOdontologica.service;

import com.backend.clinicaOdontologica.dto.entrada.TurnoEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.TurnoSalidaDto;
import com.backend.clinicaOdontologica.exception.BadRequestException;
import com.backend.clinicaOdontologica.exception.OdontologoNoEncontradoException;
import com.backend.clinicaOdontologica.exception.PacienteNoEncontradoException;

import java.util.List;

public interface ITurnoService {
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
    TurnoSalidaDto registratTurno(TurnoEntradaDto turnoEntradaDto) throws BadRequestException;

    TurnoSalidaDto registrarTurno(TurnoEntradaDto turno);

    List<TurnoSalidaDto> listarTurnos();

    TurnoSalidaDto buscarTurnoPorId(Long id);

    void eliminarTurno(Long id);

    TurnoSalidaDto actualizarTurno(Long id, TurnoEntradaDto turnoDto) throws OdontologoNoEncontradoException, PacienteNoEncontradoException;
}
