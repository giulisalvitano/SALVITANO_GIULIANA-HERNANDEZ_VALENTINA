package com.backend.clinicaOdontologica.entity;

import java.time.LocalDateTime;

@Entity
@Table(name = "TURNOS")
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @ManyToOne(mappedBy = "odontologo", cascade = CascadeType.REMOVE) //si se borra el odontologo se borrara el turno
    @JoinColumn(name = "odontologo_id") // un odontologo puede tener muchos turnos
    private Odontologo odontologo;

    @OneToOne(mappedBy = "paciente", cascade = CascadeType.REMOVE) //si se borra el paciente se borrara el turno
    @JoinColumn(name = "paciente_id") // un odontologo puede tener muchos turnos
    private Paciente paciente;
    
    private LocalDateTime fechaYHora;

    public Turno() {
    }

    public Turno(Odontologo odontologo, Paciente paciente, LocalDateTime fechaYHora) {
        this.odontologo = odontologo;
        this.paciente = paciente;
        this.fechaYHora = fechaYHora;
    }

    public Turno(int id, Odontologo odontologo, Paciente paciente, LocalDateTime fechaYHora) {
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
