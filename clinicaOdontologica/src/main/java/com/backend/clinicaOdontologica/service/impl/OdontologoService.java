package com.backend.clinicaOdontologica.service.impl;

import com.backend.clinicaOdontologica.dto.entrada.OdontologoEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.OdontologoSalidaDto;
import com.backend.clinicaOdontologica.entity.Odontologo;
import com.backend.clinicaOdontologica.exception.ResourceNotFoundException;
import com.backend.clinicaOdontologica.repository.OdontologoRepository;
import com.backend.clinicaOdontologica.service.IOdontologoService;
import com.backend.clinicaOdontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class OdontologoService implements IOdontologoService {
    private final Logger LOGGER = LoggerFactory.getLogger(OdontologoService.class);
    private final OdontologoRepository odontologoRepository;
    private final ModelMapper modelMapper;


    public OdontologoService(OdontologoRepository odontologoRepository, ModelMapper modelMapper) {
        this.odontologoRepository = odontologoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public OdontologoSalidaDto guardarOdontologo(OdontologoEntradaDto odontologo) {
        LOGGER.info("OdontologoEntradaDto: {}", JsonPrinter.toString(odontologo));
        Odontologo odGuardado = odontologoRepository.save(modelMapper.map(odontologo, Odontologo.class));
        OdontologoSalidaDto odontologoSalidaDto = modelMapper.map(odGuardado, OdontologoSalidaDto.class);
        LOGGER.info("OdontologoSalidaDto: {}", JsonPrinter.toString(odontologoSalidaDto));
        return odontologoSalidaDto;
    }



    public OdontologoSalidaDto buscarOdontologoPorId(Long id) {
        Odontologo odontologoBuscado = odontologoRepository.findById(id).orElse(null);
        OdontologoSalidaDto odontologoEncontrado = null;

        if (odontologoBuscado != null) {
            odontologoEncontrado = modelMapper.map(odontologoBuscado, OdontologoSalidaDto.class);
            LOGGER.info("Odontologo encontrado: {}", JsonPrinter.toString(odontologoEncontrado));
        } else LOGGER.error("No se ha encontrado el paciemte con id {}", id);

        return odontologoEncontrado;
    }

    public List<OdontologoSalidaDto> listarOdontologos() {

        List<OdontologoSalidaDto> odontologoSalidaDto = odontologoRepository.findAll()
                .stream()
                .map(odontologo -> modelMapper.map(odontologo, OdontologoSalidaDto.class))
                .toList();
        LOGGER.info("Listado de todos los odontolofos: {}", JsonPrinter.toString(odontologoSalidaDto));
        return odontologoSalidaDto;
    }

    public void eliminarOdontologo(Long id) throws ResourceNotFoundException {
        if (buscarOdontologoPorId(id) != null) {
            odontologoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el odontologo con id: {}", id);
        } else {
            throw new ResourceNotFoundException("No existe registro del odontologo con id {}" + id);
        }

    }

    @Override
    public OdontologoSalidaDto modificarOdontologo(OdontologoEntradaDto odontologo, Long id) throws ResourceNotFoundException {
        try {
            Optional<Odontologo> optionalOdontologo = odontologoRepository.findById(id);
            if (optionalOdontologo.isPresent()) {
                Odontologo odontologoAActualizar = optionalOdontologo.get();
                odontologoAActualizar.setApellido(odontologo.getApellido());
                odontologoAActualizar.setNombre(odontologo.getNombre());
                odontologoAActualizar.setMatricula(odontologo.getMatricula());

                Odontologo odontologoActualizado = odontologoRepository.save(odontologoAActualizar);
                LOGGER.info("Odontologo actualizado: {}", odontologoActualizado);
                return modelMapper.map(odontologoActualizado, OdontologoSalidaDto.class);
            } else {
                LOGGER.info("No se encontró el odontologo a actualizar con id: {}", id);
                throw new ResourceNotFoundException("No se encontró el odontologo a actualizar con id: " + id);
            }
        } catch (Exception ex) {
            LOGGER.error("Error al modificar el odontologo: {}", ex.getMessage());
            throw new ResourceNotFoundException("Error al modificar el odontologo");
        }
    }


}
