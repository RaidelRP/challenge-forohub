package com.alura.challenge.forohub.controller;

import jakarta.validation.constraints.NotNull;

public record DatosActualizacionUsuarioContrasena(@NotNull Long id, String contrasena) {
}
