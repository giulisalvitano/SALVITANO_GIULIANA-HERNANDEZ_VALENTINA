package com.backend.clinicaOdontologica.service.impl;

import com.backend.clinicaOdontologica.dao.IDao;
import com.backend.clinicaOdontologica.dto.entrada.PacienteEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.PacienteSalidaDto;
import com.backend.clinicaOdontologica.entity.Paciente;
import com.backend.clinicaOdontologica.service.IPacienteService;
import com.backend.clinicaOdontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PacienteService implements IPacienteService {


    private final Logger LOGGER = LoggerFactory.getLogger(PacienteService.class);


    private IDao<Paciente> pacienteIDao;

    private ModelMapper modelMapper;


    public PacienteService(IDao<Paciente> pacienteIDao, ModelMapper modelMapper) {
        this.pacienteIDao = pacienteIDao;
        this.modelMapper = modelMapper;
        configureMapping();
    }

    @Override
    public PacienteSalidaDto registrarPaciente(PacienteEntradaDto paciente) {
        //Logueamos lo que recibimos
        LOGGER.info("PacienteEntradaDto: {}", JsonPrinter.toString(paciente));
        //convertimos mediante el mapper de dtoEntrada a entidad
        Paciente pacienteEntidad = modelMapper.map(paciente, Paciente.class);
        //mandamos a persistir a la capa dao y obtenemos una entidad con ID
        Paciente pacienteEntidaConId = pacienteIDao.guardar(pacienteEntidad);
        //transformamos la entidad obtenida en salidaDto
        PacienteSalidaDto pacienteSalidaDto = modelMapper.map(pacienteEntidaConId, PacienteSalidaDto.class);
        //Logueamos lo que sale
        LOGGER.info("PacienteSalidaDto: {}",  JsonPrinter.toString(pacienteSalidaDto));
        return pacienteSalidaDto;
    }

    @Override
    public List<PacienteSalidaDto> listarPacientes() {

        List<PacienteSalidaDto> pacientesSalidaDto =  pacienteIDao.listarTodos()
                .stream()
                .map(paciente -> modelMapper.map(paciente, PacienteSalidaDto.class))
                .toList();


        LOGGER.info("Listado de todos los pacientes: {}", JsonPrinter.toString(pacientesSalidaDto));
        return pacientesSalidaDto;
    }

    @Override
    public PacienteSalidaDto buscarPacientePorId(int id) {

        Paciente pacienteBuscado = pacienteIDao.buscarPorId(id);
        PacienteSalidaDto pacienteEncontrado = null;

        if(pacienteBuscado != null){
            pacienteEncontrado = modelMapper.map(pacienteBuscado, PacienteSalidaDto.class);
            LOGGER.info("Paciente encontrado: {}", JsonPrinter.toString(pacienteEncontrado));

        } else LOGGER.error("El id no se encuentra registrado en la base de datos");


        return pacienteEncontrado;
    }


    private void configureMapping(){
        modelMapper.typeMap(PacienteEntradaDto.class, Paciente.class)
                .addMappings(mapper -> mapper.map(PacienteEntradaDto::getDomicilioEntradaDto, Paciente::setDomicilio));


        modelMapper.typeMap(Paciente.class, PacienteSalidaDto.class)
                .addMappings(mapper -> mapper.map(Paciente::getDomicilio, PacienteSalidaDto::setDomicilioSalidaDto));

    }
}
