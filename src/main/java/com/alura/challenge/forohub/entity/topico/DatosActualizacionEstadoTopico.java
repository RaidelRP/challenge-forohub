package com.alura.challenge.forohub.entity.topico;

import jakarta.validation.constraints.NotNull;

public record DatosActualizacionEstadoTopico(@NotNull Long id, Estado status) {
}
