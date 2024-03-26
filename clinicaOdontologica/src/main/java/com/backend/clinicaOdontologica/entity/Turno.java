package com.backend.clinicaOdontologica.entity;

import com.backend.clinicaOdontologica.dto.entrada.TurnoEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.TurnoSalidaDto;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TURNOS")
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "odontologo_id") // un odontologo puede tener muchos turnos
    private Odontologo odontologo;

    @ManyToOne
    @JoinColumn(name = "paciente_id") // un paciente puede tener muchos turnos
    private Paciente paciente;
    
    private LocalDateTime fechaYHora;

    public Turno() {
    }

    public Turno(Odontologo odontologo, Paciente paciente, LocalDateTime fechaYHora) {
        this.odontologo = odontologo;
        this.paciente = paciente;
        this.fechaYHora = fechaYHora;
    }

    public Turno(Long id, Odontologo odontologo, Paciente paciente, LocalDateTime fechaYHora) {
        this.id = id;
        this.odontologo = odontologo;
        this.paciente = paciente;
        this.fechaYHora = fechaYHora;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public Odontologo getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(Odontologo odontologo) {
        this.odontologo = odontologo;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public LocalDateTime getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(LocalDateTime fechaYHora) {
        this.fechaYHora = fechaYHora;
    }




//    @Override
//    public String toString() {
//        return "Turno{" +
//                "id=" + id +
//                ", odontologo=" + odontologo +
//                ", paciente=" + paciente +
//                ", fechaYHora=" + fechaYHora +
//                '}';
//    }


    ModelMapper modelMapper;
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

}
