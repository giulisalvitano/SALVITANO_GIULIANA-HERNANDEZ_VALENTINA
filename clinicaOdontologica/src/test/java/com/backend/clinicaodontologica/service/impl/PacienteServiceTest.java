package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaOdontologica.dto.entrada.DomicilioEntradaDto;
import com.backend.clinicaOdontologica.dto.entrada.PacienteEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.PacienteSalidaDto;
import com.backend.clinicaOdontologica.service.impl.PacienteService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class PacienteServiceTest {

    @Autowired
    private PacienteService pacienteService;


    @Test
    @Order(1)
    void deberiaRegistrarseUnPacienteDeNombreJuan_yRetornarSuId() {
        //arrange
        PacienteEntradaDto pacienteEntradaDto = new PacienteEntradaDto("Juan", "Perez", 123456, LocalDate.of(2024, 3, 22), new DomicilioEntradaDto("Calle", 1234, "Localidad", "Provincia"));

        //act
        PacienteSalidaDto pacienteSalidaDto = pacienteService.registrarPaciente(pacienteEntradaDto);

        //assert
        assertNotNull(pacienteSalidaDto);
        assertNotNull(pacienteSalidaDto.getId());
        assertEquals("Juan", pacienteSalidaDto.getNombre());

    }

    @Test
    @Order(2)
    void deberiaDevolverUnaListaNoVaciaDePacientes() {
        // Act
        List<PacienteSalidaDto> pacientes = pacienteService.listarPacientes();

        // Assert
        Assertions.assertFalse(pacientes.isEmpty());
    }



    @Test
    @Order(3)
    void deberiaEliminarseElPacienteConId1() {

        assertDoesNotThrow(() -> pacienteService.eliminarPaciente(1L));
    }


    @Test
    @Order(4)
    void deberiaDevolverUnaListaVaciaDePacientes() {
        List<PacienteSalidaDto> pacientes = pacienteService.listarPacientes();

        assertTrue(pacientes.isEmpty());
    }



}