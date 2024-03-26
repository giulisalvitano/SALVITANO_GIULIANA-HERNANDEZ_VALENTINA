package com.backend.clinicaOdontologica.dto.entrada;

import com.backend.clinicaOdontologica.dto.salida.TurnoSalidaDto;
import com.backend.clinicaOdontologica.entity.Odontologo;
import com.backend.clinicaOdontologica.entity.Paciente;
import com.backend.clinicaOdontologica.entity.Turno;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;



public class TurnoEntradaDto {
    @NotNull(message = "El ID del odontólogo no puede ser nulo")
    private Long idOdontologo;

    @NotNull(message = "El ID del paciente no puede ser nulo")
    private Long idPaciente;

    @FutureOrPresent(message = "La fecha no puede ser anterior al día de hoy")
    @NotNull(message = "Debe especificarse la fecha y hora del turno")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime fechaYHora;

    public TurnoEntradaDto() {
    }

    public TurnoEntradaDto(Long idOdontologo, Long idPaciente, LocalDateTime fechaYHora) {
        this.idOdontologo = idOdontologo;
        this.idPaciente = idPaciente;
        this.fechaYHora = fechaYHora;

    }

    public Long getIdOdontologo() {
        return idOdontologo;
    }

    public void setIdOdontologo(Long idOdontologo) {
        this.idOdontologo = idOdontologo;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public LocalDateTime getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(LocalDateTime fechaYHora) {
        this.fechaYHora = fechaYHora;
    }




    ModelMapper modelMapper = new ModelMapper();



    private void configureMapping() {
        modelMapper.getConfiguration()
                .setPropertyCondition(Conditions.isNotNull())
                .setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.createTypeMap(TurnoEntradaDto.class, Turno.class)
                .addMappings(mapping -> {
                    mapping.skip(Turno::setPaciente);
                    mapping.skip(Turno::setOdontologo);
                })
                .setPropertyCondition(Conditions.isNotNull()) // Establecer la condición para mapear solo si las propiedades no son nulas
                .setPostConverter(context -> {
                    TurnoEntradaDto source = context.getSource();
                    Turno destination = context.getDestination();
                    if (source.getIdPaciente() != null) {
                        Paciente paciente = new Paciente();
                        paciente.setId(source.getIdPaciente());
                        destination.setPaciente(paciente); // Asignar el ID del paciente al turno
                    }
                    if (source.getIdOdontologo() != null) {
                        Odontologo odontologo = new Odontologo();
                        odontologo.setId(source.getIdOdontologo());
                        destination.setOdontologo(odontologo); // Asignar el ID del odontólogo al turno
                    }
                    return destination;
                });

        modelMapper.createTypeMap(Turno.class, TurnoSalidaDto.class)
                .addMappings(mapping -> mapping.skip(TurnoSalidaDto::setId));
    }



//    private void configureMapping() {
//        modelMapper.getConfiguration()
//                .setPropertyCondition(Conditions.isNotNull())
//                .setMatchingStrategy(MatchingStrategies.STRICT);
//
//        modelMapper.createTypeMap(TurnoEntradaDto.class, Turno.class)
//                .addMappings(mapping -> mapping.skip(Turno::setPaciente, Turno::setOdontologo));
//
//        modelMapper.createTypeMap(Turno.class, TurnoSalidaDto.class)
//                .addMappings(mapping -> mapping.skip(TurnoSalidaDto::setId));
//    }
//



//modelMapper.createTypeMap(TurnoEntradaDto.class, Turno.class)
//            .addMappings(mapping -> mapping.skip(Turno::setId));
//
//    Turno turno = modelMapper.map(turnoEntradaDto, Turno.class);
//turno.setOdontologo(new Odontologo(turnoEntradaDto.getIdOdontologo()));
//turno.setPaciente(new Paciente(turnoEntradaDto.getIdPaciente()));
//




}

