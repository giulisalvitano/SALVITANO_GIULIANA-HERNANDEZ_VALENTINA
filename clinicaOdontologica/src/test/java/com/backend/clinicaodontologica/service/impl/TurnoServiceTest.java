package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaOdontologica.dto.entrada.DomicilioEntradaDto;
import com.backend.clinicaOdontologica.dto.entrada.OdontologoEntradaDto;
import com.backend.clinicaOdontologica.dto.entrada.PacienteEntradaDto;
import com.backend.clinicaOdontologica.dto.entrada.TurnoEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.OdontologoSalidaDto;
import com.backend.clinicaOdontologica.dto.salida.PacienteSalidaDto;
import com.backend.clinicaOdontologica.dto.salida.TurnoSalidaDto;
import com.backend.clinicaOdontologica.exception.BadRequestException;
import com.backend.clinicaOdontologica.exception.ResourceNotFoundException;
import com.backend.clinicaOdontologica.service.impl.OdontologoService;
import com.backend.clinicaOdontologica.service.impl.PacienteService;
import com.backend.clinicaOdontologica.service.impl.TurnoService;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application-test.properties")


public class TurnoServiceTest {

    @Autowired
    private TurnoService turnoService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private OdontologoService odontologoService;

    @Test
    @Order(1)
    void deberiaRegistrarseUnTurnoConUnPacienteDeNombreJuanYUnOdontologoDeNombreJose() {
        // Arrange: Registrar un odontólogo con el nombre "Jose"
        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto("JP24O2000", "Jose", "Perez");
        OdontologoSalidaDto odontologoSalidaDto = odontologoService.guardarOdontologo(odontologoEntradaDto);
        assertNotNull(odontologoSalidaDto);
        assertNotNull(odontologoSalidaDto.getId());

        // Arrange: Registrar un paciente con el nombre "Juan"
        PacienteEntradaDto pacienteEntradaDto = new PacienteEntradaDto("Juan", "Perez", 123456, LocalDate.of(2024, 3, 22), new DomicilioEntradaDto("Calle", 1234, "Localidad", "Provincia"));
        PacienteSalidaDto pacienteSalidaDto = pacienteService.registrarPaciente(pacienteEntradaDto);
        assertNotNull(pacienteSalidaDto);
        assertNotNull(pacienteSalidaDto.getId());

        // Obtener los IDs del paciente y del odontólogo registrados
        Long idPaciente = pacienteSalidaDto.getId();
        Long idOdontologo = odontologoSalidaDto.getId();

        // Act: Registrar un turno con el paciente y el odontólogo previamente registrados
        LocalDateTime fechaYHora = LocalDateTime.now();
        TurnoEntradaDto turnoEntradaDto = new TurnoEntradaDto(idOdontologo, idPaciente, fechaYHora);

        // Act: Intentar registrar el turno
        TurnoSalidaDto turnoSalidaDto = null;
        try {
            turnoSalidaDto = turnoService.registrarTurno(turnoEntradaDto);
        } catch (BadRequestException e) {
            // Manejar la excepción si ocurre
            fail("Error al registrar el turno: " + e.getMessage());
        }

        // Assert: Verificar que el turno se haya registrado correctamente
        assertNotNull(turnoSalidaDto);
        assertNotNull(turnoSalidaDto.getId());
    }


    @Test
    @Order(2)
    void deberiaDevolverUnaListaNoVaciaDeTurnos() {
        // Act
        List<TurnoSalidaDto> turnos = turnoService.listarTurnos();

        // Assert
        Assertions.assertFalse(turnos.isEmpty());
    }

    @Test
    @Order(3)
    void deberiaEliminarseElTurnoConId1() {


        assertDoesNotThrow(() -> turnoService.eliminarTurno(1L));
    }


    @Test
    @Order(4)
    void deberiaDevolverUnaListaVaciaDeTurnos() {
        List<TurnoSalidaDto> turnos = turnoService.listarTurnos();

        assertTrue(turnos.isEmpty());
    }


    @Test
    @Order(5)
    void deberiaModificarUnTurnoExistente() throws BadRequestException {
        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto("JP24O2001", "Pedro", "Gonzalez");
        OdontologoSalidaDto odontologoSalidaDto = odontologoService.guardarOdontologo(odontologoEntradaDto);
        assertNotNull(odontologoSalidaDto);
        assertNotNull(odontologoSalidaDto.getId());

        PacienteEntradaDto pacienteEntradaDto = new PacienteEntradaDto("Ana", "Lopez", 654321, LocalDate.of(2024, 3, 23), new DomicilioEntradaDto("Avenida", 4321, "Ciudad", "Provincia"));
        PacienteSalidaDto pacienteSalidaDto = pacienteService.registrarPaciente(pacienteEntradaDto);
        assertNotNull(pacienteSalidaDto);
        assertNotNull(pacienteSalidaDto.getId());

        LocalDateTime fechaYHoraInicial = LocalDateTime.of(2024, 3, 30, 10, 0);
        TurnoEntradaDto turnoInicialDto = new TurnoEntradaDto(odontologoSalidaDto.getId(), pacienteSalidaDto.getId(), fechaYHoraInicial);
        TurnoSalidaDto turnoInicialSalidaDto = turnoService.registrarTurno(turnoInicialDto);
        assertNotNull(turnoInicialSalidaDto);
        assertNotNull(turnoInicialSalidaDto.getId());


        LocalDateTime nuevaFechaYHora = LocalDateTime.of(2024, 4, 1, 9, 0);
        TurnoEntradaDto turnoModificadoDto = new TurnoEntradaDto(odontologoSalidaDto.getId(), pacienteSalidaDto.getId(), nuevaFechaYHora);
        TurnoSalidaDto turnoModificadoSalidaDto = null;
        try {
            turnoModificadoSalidaDto = turnoService.modificarTurno(turnoInicialSalidaDto.getId(), turnoModificadoDto);
        } catch (ResourceNotFoundException e) {
            fail("Error al modificar el turno: " + e.getMessage());
        }

        assertNotNull(turnoModificadoSalidaDto);
        assertNotNull(turnoModificadoSalidaDto.getId());
        assertEquals(turnoInicialSalidaDto.getId(), turnoModificadoSalidaDto.getId());
        assertEquals(nuevaFechaYHora, turnoModificadoSalidaDto.getFechaYHora());
    }





}