package com.alura.challenge.forohub.infra.exceptions;

public class AutenticacionException extends RuntimeException {
    public AutenticacionException(String mensaje) {
        super(mensaje);
    }
}
