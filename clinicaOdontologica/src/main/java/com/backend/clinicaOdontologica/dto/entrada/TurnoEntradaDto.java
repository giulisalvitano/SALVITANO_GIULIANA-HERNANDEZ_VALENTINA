package com.backend.clinicaOdontologica.dto.entrada;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class TurnoEntradaDto {
    @NotNull(message = "El ID del odontólogo no puede ser nulo")
    private Long idOdontologo;

    @NotNull(message = "El ID del paciente no puede ser nulo")
    private Long idPaciente;

    @NotNull(message = "La fecha y hora del turno no puede ser nula")
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

}
