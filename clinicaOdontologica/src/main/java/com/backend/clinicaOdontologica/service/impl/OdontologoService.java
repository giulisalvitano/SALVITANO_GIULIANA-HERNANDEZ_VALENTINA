package com.backend.clinicaOdontologica.service.impl;

import com.backend.clinicaOdontologica.entity.Odontologo;
import com.backend.clinicaOdontologica.repository.OdontologoRepository;
import com.backend.clinicaOdontologica.service.IOdontologoService;
import org.apache.logging.log4j.core.Logger;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OdontologoService implements IOdontologoService {
    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(OdontologoService.class);
    private OdontologoRepository odontologoRepository;
    private ModelMapper modelMapper;

    public OdontologoService(OdontologoRepository odontologoRepository, ModelMapper modelMapper) {
        this.odontologoRepository = odontologoRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public Odontologo guardarOdontologo(Odontologo odontologo) {
        return odontologoRepository.save(odontologo);
    }


    public List<Odontologo> listarOdontologos() {
        return odontologoRepository.findAll();
    }


}

