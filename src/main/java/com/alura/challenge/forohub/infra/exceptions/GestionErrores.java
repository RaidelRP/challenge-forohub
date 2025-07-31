package com.alura.challenge.forohub.infra.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class GestionErrores {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity gestionarError404(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity gestionarError400(MethodArgumentNotValidException ex) {
        var errores = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(errores.stream().map(DatosErrorValidacion::new).toList());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity gestionarError403(AccessDeniedException ex) {
        return ResponseEntity.status(403).body(ex.getMessage());
    }


    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity gestionarErrorIntegrityConstraintViolation(SQLIntegrityConstraintViolationException e) {
        return ResponseEntity.badRequest()
                .body("Ha ocurrido una violación a la integridad de la base de datos: " + e.getMessage());
    }

    @ExceptionHandler(ValidacionException.class)
    public ResponseEntity tratarErrorDeValidacion(ValidacionException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    public record DatosErrorValidacion(String campo, String mensaje) {
        public DatosErrorValidacion(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
