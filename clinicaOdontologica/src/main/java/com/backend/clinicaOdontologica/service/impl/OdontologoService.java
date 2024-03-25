package com.backend.clinicaOdontologica.service.impl;

import com.backend.clinicaOdontologica.dto.entrada.OdontologoEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.OdontologoSalidaDto;
import com.backend.clinicaOdontologica.entity.Odontologo;
import com.backend.clinicaOdontologica.exception.ResourceNotFoundException;
import com.backend.clinicaOdontologica.repository.OdontologoRepository;
import com.backend.clinicaOdontologica.service.IOdontologoService;
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

    public OdontologoSalidaDto guardarOdontologo(OdontologoEntradaDto odontologo) {
        Odontologo odGuardado = odontologoRepository.save(modelMapper.map(odontologo, Odontologo.class));
        OdontologoSalidaDto odontologoSalidaDto = modelMapper.map(odGuardado, OdontologoSalidaDto.class);
        LOGGER.info("Odontologo guardado: {}", odontologoSalidaDto);
        return odontologoSalidaDto;
    }

    public OdontologoSalidaDto buscarOdontologoPorId(Long id) {
        Odontologo odontologoBuscado = odontologoRepository.findById(id).orElse(null);

        OdontologoSalidaDto odontologoSalidaDto = null;
        if (odontologoBuscado != null) {
            odontologoSalidaDto = modelMapper.map(odontologoBuscado, OdontologoSalidaDto.class);
            LOGGER.info("Odontologo encontrado: {}", odontologoSalidaDto);
        } else LOGGER.error("El id no se encuentra registrado en la base de datos");

        return odontologoSalidaDto;
    }

    public List<OdontologoSalidaDto> listarOdontologos() {
        List<OdontologoSalidaDto> odontologos = odontologoRepository.findAll().stream()
                .map(o -> modelMapper.map(o, OdontologoSalidaDto.class)).toList();

        LOGGER.info("Listado de todos los odontologos: {}", odontologos);

        return odontologos;
    }

    public void eliminarOdontologo(Long id)  {
        if (buscarOdontologoPorId(id) != null) {
            odontologoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el odontologo con id: {}", id);
        } else {
            LOGGER.error("No se ha encontrado el odontologo con id {}", id);

        }

    }


//    @Override
//    public OdontologoSalidaDto modificarOdontologo(OdontologoEntradaDto odontologoEntradaDto, Long id) {
//        Odontologo odontologoRecibido = modelMapper.map(odontologoEntradaDto, Odontologo.class);
//        Odontologo odontologoAActualizar = odontologoRepository.findById(id).orElse(null);
//        OdontologoSalidaDto odontologoSalidaDto = null;
//
//        if (odontologoAActualizar != null) {
//
//            odontologoAActualizar = odontologoRecibido;
//            odontologoRepository.save(odontologoAActualizar);
//
//            odontologoSalidaDto = modelMapper.map(odontologoAActualizar, OdontologoSalidaDto.class);
//
//            LOGGER.warn("Odontologo actualizado: {}", odontologoSalidaDto);
//
//        } else {
//            LOGGER.error("No fue posible actualizar los datos ya que el odontologo no se encuentra registrado");
//
//        }
//
//
//        return odontologoSalidaDto;
//    }

    @Override
    public OdontologoSalidaDto modificarOdontologo(OdontologoEntradaDto odontologo, Long id) {
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
            return null;
        }
    }




}
