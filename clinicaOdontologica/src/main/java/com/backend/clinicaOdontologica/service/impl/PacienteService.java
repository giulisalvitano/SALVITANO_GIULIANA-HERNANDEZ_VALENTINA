package com.backend.clinicaOdontologica.service.impl;

import com.backend.clinicaOdontologica.dto.entrada.PacienteEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.PacienteSalidaDto;
import com.backend.clinicaOdontologica.entity.Paciente;
import com.backend.clinicaOdontologica.exception.ResourceNotFoundException;
import com.backend.clinicaOdontologica.repository.PacienteRepository;
import com.backend.clinicaOdontologica.service.IPacienteService;
import com.backend.clinicaOdontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService implements IPacienteService {


    private final Logger LOGGER = LoggerFactory.getLogger(PacienteService.class);


    private PacienteRepository pacienteRepository;

    private ModelMapper modelMapper;


    public PacienteService(PacienteRepository pacienteRepository, ModelMapper modelMapper) {
        this.pacienteRepository = pacienteRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }

    @Override
    public PacienteSalidaDto registrarPaciente(PacienteEntradaDto paciente) {
        LOGGER.info("PacienteEntradaDto: {}", JsonPrinter.toString(paciente));
        Paciente pacienteEntidad = modelMapper.map(paciente, Paciente.class);
        Paciente pacienteEntidaConId = pacienteRepository.save(pacienteEntidad);
        PacienteSalidaDto pacienteSalidaDto = modelMapper.map(pacienteEntidaConId, PacienteSalidaDto.class);
        LOGGER.info("PacienteSalidaDto: {}", JsonPrinter.toString(pacienteSalidaDto));
        return pacienteSalidaDto;
    }

    @Override
    public List<PacienteSalidaDto> listarPacientes() {

        List<PacienteSalidaDto> pacientesSalidaDto = pacienteRepository.findAll()
                .stream()
                .map(paciente -> modelMapper.map(paciente, PacienteSalidaDto.class))
                .toList();

        LOGGER.info("Listado de todos los pacientes: {}", JsonPrinter.toString(pacientesSalidaDto));
        return pacientesSalidaDto;
    }


    @Override
    public PacienteSalidaDto buscarPacientePorId(Long id) {

        Paciente pacienteBuscado = pacienteRepository.findById(id).orElse(null);
        PacienteSalidaDto pacienteEncontrado = null;

        if (pacienteBuscado != null) {
            pacienteEncontrado = modelMapper.map(pacienteBuscado, PacienteSalidaDto.class);
            LOGGER.info("Paciente encontrado: {}", JsonPrinter.toString(pacienteEncontrado));

        } else LOGGER.error("No se ha encontrado el paciemte con id {}", id);

        return pacienteEncontrado;
    }

    @Override
    public void eliminarPaciente(Long id) throws ResourceNotFoundException {
        if (buscarPacientePorId(id) != null) {
            pacienteRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el paciente con id {}", id);
        } else {
            throw new ResourceNotFoundException("No existe registro del paciente con id {}" + id);
        }

    }

    @Override
    public PacienteSalidaDto modificarPaciente(PacienteEntradaDto pacienteEntradaDto, Long id) throws ResourceNotFoundException {
        Paciente pacienteRecibido = modelMapper.map(pacienteEntradaDto, Paciente.class);
        Paciente pacienteAActualizar = pacienteRepository.findById(id).orElse(null);

        PacienteSalidaDto pacienteSalidaDto = null;

        if (pacienteAActualizar != null) {
            pacienteAActualizar.setNombre(pacienteRecibido.getNombre());
            pacienteAActualizar.setApellido(pacienteRecibido.getApellido());
            pacienteAActualizar.setDni(pacienteRecibido.getDni());
            pacienteAActualizar.setFechaIngreso(pacienteRecibido.getFechaIngreso());
            pacienteAActualizar.getDomicilio().setCalle(pacienteRecibido.getDomicilio().getCalle());
            pacienteAActualizar.getDomicilio().setNumero(pacienteRecibido.getDomicilio().getNumero());
            pacienteAActualizar.getDomicilio().setLocalidad(pacienteRecibido.getDomicilio().getLocalidad());
            pacienteAActualizar.getDomicilio().setProvincia(pacienteRecibido.getDomicilio().getProvincia());

            pacienteRepository.save(pacienteAActualizar);

            pacienteSalidaDto = modelMapper.map(pacienteAActualizar, PacienteSalidaDto.class);
            LOGGER.warn("Paciente actualizado: {}", JsonPrinter.toString(pacienteSalidaDto));

        } else {
            LOGGER.error("No fue posible actualizar el paciente porque no se encuentra en nuestra base de datos");
            throw new ResourceNotFoundException("No es posible actualizar el paciente con id " + id + " ya que no se encuentra en nuestra base de datos");
        }
        return pacienteSalidaDto;
    }



    private void configureMapping() {
    modelMapper.typeMap(PacienteEntradaDto.class, Paciente.class)
            .addMappings(mapper -> mapper.map(PacienteEntradaDto::getDomicilioEntradaDto, Paciente::setDomicilio));


    modelMapper.typeMap(Paciente.class, PacienteSalidaDto.class)
            .addMappings(mapper -> mapper.map(Paciente::getDomicilio, PacienteSalidaDto::setDomicilioSalidaDto));

}
}
