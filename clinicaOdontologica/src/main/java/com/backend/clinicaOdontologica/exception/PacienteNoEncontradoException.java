package com.backend.clinicaOdontologica.exception;

public class PacienteNoEncontradoException extends RuntimeException {

    public PacienteNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
