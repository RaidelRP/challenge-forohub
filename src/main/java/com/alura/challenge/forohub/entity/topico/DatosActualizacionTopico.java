package com.alura.challenge.forohub.entity.topico;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosActualizacionTopico(
        @NotNull Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        Estado status,
        Long idAutor) {
}
