package com.alura.challenge.forohub.entity.usuario;

import jakarta.validation.constraints.NotNull;

public record DatosActualizacionUsuario(
        @NotNull Long id,
        String nombre,
        String email) {
}
