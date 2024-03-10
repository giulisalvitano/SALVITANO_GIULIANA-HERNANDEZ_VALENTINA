package com.backend.clinicaOdontologica.dto.salida;

import com.backend.clinicaOdontologica.entity.Odontologo;
import com.backend.clinicaOdontologica.entity.Paciente;

import java.time.LocalDateTime;

public class TurnoSalidaDto {
    private int id;
    private Odontologo odontologo;
    private Paciente paciente;
    private LocalDateTime fechaYHora;

    public TurnoSalidaDto(int id, Odontologo odontologo, Paciente paciente, LocalDateTime fechaYHora) {
        this.id = id;
        this.odontologo = odontologo;
        this.paciente = paciente;
        this.fechaYHora = fechaYHora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
