package com.backend.clinicaodontologica.service.impl;


import com.backend.clinicaOdontologica.dto.entrada.DomicilioEntradaDto;
import com.backend.clinicaOdontologica.dto.entrada.OdontologoEntradaDto;
import com.backend.clinicaOdontologica.dto.entrada.PacienteEntradaDto;
import com.backend.clinicaOdontologica.dto.entrada.TurnoEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.OdontologoSalidaDto;
import com.backend.clinicaOdontologica.dto.salida.PacienteSalidaDto;
import com.backend.clinicaOdontologica.dto.salida.TurnoSalidaDto;
import com.backend.clinicaOdontologica.exception.BadRequestException;
import com.backend.clinicaOdontologica.service.impl.OdontologoService;
import com.backend.clinicaOdontologica.service.impl.PacienteService;
import com.backend.clinicaOdontologica.service.impl.TurnoService;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


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
    void deberiaRegistrarseUnTurnoConUnPacienteDeNombreJuanyUnOdontologoDeNombreJose() {
        // Registrar un odontólogo con el nombre "Jose"
        //arrange
        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto("JP24O2000", "Jose", "Perez");
        //act
        OdontologoSalidaDto odontologoSalidaDto = odontologoService.guardarOdontologo(odontologoEntradaDto);

        // Arrange
        // Registrar un paciente con el nombre "Juan"
        PacienteEntradaDto pacienteEntradaDto = new PacienteEntradaDto("Juan", "Perez", 123456, LocalDate.of(2024, 3, 22), new DomicilioEntradaDto("Calle", 1234, "Localidad", "Provincia"));
        PacienteSalidaDto pacienteSalidaDto = pacienteService.registrarPaciente(pacienteEntradaDto);


        // Verificar que los pacientes y odontólogos se hayan registrado correctamente
        assertNotNull(pacienteSalidaDto);
        pacienteSalidaDto.getId();
        assertNotNull(odontologoSalidaDto);
        assertNotNull(odontologoSalidaDto.getId());

        // Obtener los IDs del paciente y del odontólogo registrados
        Long idPaciente = pacienteSalidaDto.getId();
        Long idOdontologo = odontologoSalidaDto.getId();
        LocalDateTime fechaYHora = LocalDateTime.now();

        // Act
        TurnoEntradaDto turnoEntradaDto = new TurnoEntradaDto(idOdontologo, idPaciente, fechaYHora);

        // Act
        TurnoSalidaDto turnoSalidaDto = null;
        try {
            turnoSalidaDto = turnoService.registrarTurno(turnoEntradaDto);
        } catch (BadRequestException e) {
            // Manejar la excepción (puedes imprimir un mensaje de error, registrar el error, etc.)
            System.out.println("Error al registrar el turno: " + e.getMessage());
        }

        // Assert
        assertNotNull(turnoSalidaDto);
        assertNotNull(turnoSalidaDto.getId());
    }


//@Test
//@Order(1)
//void deberiaRegistrarseUnTurnoConUnPacienteDeNombreJuanyUnOdontologoDeNombreJose() {
//    // Arrange
//    Long idOdontologo = 1L; // ID del odontólogo "Jose"
//    Long idPaciente = 1L;   // ID del paciente "Juan"
//    LocalDateTime fechaYHora = LocalDateTime.now();
//
//    TurnoEntradaDto turnoEntradaDto = new TurnoEntradaDto(idOdontologo, idPaciente, fechaYHora);
//
//    // Act
//    TurnoSalidaDto turnoSalidaDto = null;
//    try {
//        turnoSalidaDto = turnoService.registrarTurno(turnoEntradaDto);
//    } catch (BadRequestException e) {
//        // Manejar la excepción (puedes imprimir un mensaje de error, registrar el error, etc.)
//        System.out.println("Error al registrar el turno: " + e.getMessage());
//    }
//
//    // Assert
//    assertNotNull(turnoSalidaDto);
//    assertNotNull(turnoSalidaDto.getId());
}