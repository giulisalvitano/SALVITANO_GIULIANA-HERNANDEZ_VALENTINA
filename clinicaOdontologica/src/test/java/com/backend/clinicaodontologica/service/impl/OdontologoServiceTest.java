package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaOdontologica.dto.entrada.OdontologoEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.OdontologoSalidaDto;
import com.backend.clinicaOdontologica.service.impl.OdontologoService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class OdontologoServiceTest {

    @Autowired
    private OdontologoService odontologoService;

    @Test
    @Order(1)
    void deberiaRegistrarseUnOdontologoConElNombreDeJose_yRetornarSuId() {
        //arrange
        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto("JP24O2000", "Jose", "Perez");

        //act
        OdontologoSalidaDto odontologoSalidaDto = odontologoService.guardarOdontologo(odontologoEntradaDto);

        //assert
        assertNotNull(odontologoSalidaDto);
        assertNotNull(odontologoSalidaDto.getId());
        assertEquals("Jose", odontologoSalidaDto.getNombre());

    }


    @Test
    @Order(2)
    void deberiaDevolverUnaListaNoVaciaDeOdontologos() {
        // Act
        List<OdontologoSalidaDto> odontologos = odontologoService.listarOdontologos();

        // Assert
        Assertions.assertFalse(odontologos.isEmpty());
    }


    @Test
    @Order(3)
    void deberiaEliminarseElOdontologoConID1() {

        assertDoesNotThrow(() -> odontologoService.eliminarOdontologo(1L));
    }

    @Test
    @Order(4)
    void deberiaDevolverUnaListaVaciaDeOdontologos() {
        List<OdontologoSalidaDto> odontologos = odontologoService.listarOdontologos();

        assertTrue(odontologos.isEmpty());
    }



}