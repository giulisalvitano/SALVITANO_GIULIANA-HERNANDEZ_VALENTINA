package com.backend.clinicaOdontologica.controller;

import com.backend.clinicaOdontologica.dto.entrada.OdontologoEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.OdontologoSalidaDto;
import com.backend.clinicaOdontologica.service.impl.OdontologoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {
    private final OdontologoService odontologoService;

    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    //POST

    @PostMapping("registrar")

    public ResponseEntity<OdontologoSalidaDto> guardarOdontologo(@Valid @RequestBody OdontologoEntradaDto odontologo) {
        return new ResponseEntity<>(odontologoService.guardarOdontologo(odontologo), HttpStatus.CREATED);
    }

    //PUT

    @PutMapping("actualizar/{id}")
    public ResponseEntity<OdontologoSalidaDto> actualizarOdontologo(@Valid @RequestBody OdontologoEntradaDto odontologo, @PathVariable Long id) {
        return null;
    }

    //GET

    @GetMapping("{id}")
    public ResponseEntity<OdontologoSalidaDto> obtenerOdontologoPorId(@PathVariable Long id) {
        return new ResponseEntity<>(odontologoService.buscarOdontologoPorId(id), HttpStatus.OK);
    }


    @GetMapping()

    public ResponseEntity<List<OdontologoSalidaDto>> listarOdontologos() {
        return new ResponseEntity<>(odontologoService.listarOdontologos(), HttpStatus.OK);
    }

    //DELETE

    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?> eliminarOdontologo(@PathVariable Long id) {
        odontologoService.eliminarOdontologo(id);
        return new ResponseEntity<>("Odontologo eliminado correctamente", HttpStatus.NO_CONTENT);

    }

}
