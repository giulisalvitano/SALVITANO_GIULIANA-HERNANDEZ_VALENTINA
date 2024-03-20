package com.backend.clinicaOdontologica.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String mensaje, Long id) {
        super(mensaje);
    }
}
