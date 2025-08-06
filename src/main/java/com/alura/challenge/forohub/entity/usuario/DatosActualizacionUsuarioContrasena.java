package com.alura.challenge.forohub.entity.usuario;

import jakarta.validation.constraints.NotNull;

public record DatosActualizacionUsuarioContrasena(@NotNull Long id, String contrasena) {
}
