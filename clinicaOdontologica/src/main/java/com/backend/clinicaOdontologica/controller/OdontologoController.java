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

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

    private final IOdontologoService odontologoService;
    private final ModelMapper modelMapper;

    public OdontologoController(IOdontologoService odontologoService, ModelMapper modelMapper) {
        this.odontologoService = odontologoService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<OdontologoSalidaDto> guardarOdontologo(@Validated @RequestBody OdontologoEntradaDto odontologoDto) {
        Odontologo odontologo = modelMapper.map(odontologoDto, Odontologo.class);
        Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologo);
        OdontologoSalidaDto odontologoSalidaDto = modelMapper.map(odontologoGuardado, OdontologoSalidaDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(odontologoSalidaDto);
    }

    @GetMapping
    public ResponseEntity<List<OdontologoSalidaDto>> listarOdontologos() {
        List<Odontologo> odontologos = odontologoService.listarOdontologos();
        List<OdontologoSalidaDto> odontologoSalidaDtos = odontologos.stream()
                .map(odontologo -> modelMapper.map(odontologo, OdontologoSalidaDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(odontologoSalidaDtos);
    }
}
