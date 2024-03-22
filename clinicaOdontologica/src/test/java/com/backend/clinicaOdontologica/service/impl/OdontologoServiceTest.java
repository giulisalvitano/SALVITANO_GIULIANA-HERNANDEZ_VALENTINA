package com.backend.clinicaOdontologica.service.impl;

import org.junit.jupiter.api.Test;
import com.backend.clinicaOdontologica.entity.Odontologo;
import com.backend.clinicaOdontologica.service.impl.OdontologoService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
/*

class OdontologoServiceTest {

    @Test
    public void listarTodosLosOdontologosEnH2() {
        OdontologoService odontologoService = new OdontologoService(new OdontologoDaoH2());
        List<Odontologo> listaDeOdontologos = odontologoService.listarOdontologos();
        assertNotNull(listaDeOdontologos);
        assertEquals(2, listaDeOdontologos.size());
    }


    @Test
    public void listarTodosLosOdontologosEnMemoria() {
        List<Odontologo> listaDeOdontologos = List.of(new Odontologo("87858", "ValGiu", "HerSal"));
        OdontologoService odontologoService = new OdontologoService(new OdontologoDaoMemoria(listaDeOdontologos));
        List<Odontologo> todosLosOdontologos = odontologoService.listarOdontologos();
        assertNotNull(todosLosOdontologos);
        assertEquals(1, todosLosOdontologos.size());
    }


}*/