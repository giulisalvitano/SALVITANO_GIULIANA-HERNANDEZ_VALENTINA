package com.backend.clinicaOdontologica.controller;

import com.backend.clinicaOdontologica.dto.entrada.OdontologoEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.OdontologoSalidaDto;
import com.backend.clinicaOdontologica.entity.Odontologo;
import com.backend.clinicaOdontologica.service.IOdontologoService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

// Controlador para manejar las solicitudes relacionadas con los odontólogos
@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

    private final IOdontologoService odontologoService; // Servicio para realizar operaciones con odontólogos
    private final ModelMapper modelMapper; // Mapper para realizar mapeos entre objetos

    // Constructor del controlador que recibe el servicio y el modelMapper
    public OdontologoController(IOdontologoService odontologoService, ModelMapper modelMapper) {
        this.odontologoService = odontologoService;
        this.modelMapper = modelMapper;
    }

    // Endpoint para guardar un odontólogo
    @PostMapping
    public ResponseEntity<OdontologoSalidaDto> guardarOdontologo(@Validated @RequestBody OdontologoEntradaDto odontologoDto) {
        // Mapea el objeto OdontologoEntradaDto a la entidad Odontologo
        Odontologo odontologo = modelMapper.map(odontologoDto, Odontologo.class);
        // Guarda el odontólogo utilizando el servicio
        Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologo);
        // Mapea el odontólogo guardado a un objeto OdontologoSalidaDto
        OdontologoSalidaDto odontologoSalidaDto = modelMapper.map(odontologoGuardado, OdontologoSalidaDto.class);
        // Retorna una respuesta con el objeto OdontologoSalidaDto y un código de estado 201 (CREATED)
        return ResponseEntity.status(HttpStatus.CREATED).body(odontologoSalidaDto);
    }

    // Endpoint para listar todos los odontólogos
    @GetMapping
    public ResponseEntity<List<OdontologoSalidaDto>> listarOdontologos() {
        // Obtiene la lista de odontólogos utilizando el servicio
        List<Odontologo> odontologos = odontologoService.listarOdontologos();
        // Mapea la lista de odontólogos a una lista de OdontologoSalidaDto
        List<OdontologoSalidaDto> odontologoSalidaDtos = odontologos.stream()
                .map(odontologo -> modelMapper.map(odontologo, OdontologoSalidaDto.class))
                .collect(Collectors.toList());
        // Retorna una respuesta con la lista de OdontologoSalidaDto y un código de estado 200 (OK)
        return ResponseEntity.ok(odontologoSalidaDtos);
    }
}
