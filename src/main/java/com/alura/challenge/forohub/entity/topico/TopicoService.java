package com.alura.challenge.forohub.entity.topico;

import com.alura.challenge.forohub.repository.TopicoRepository;
import com.alura.challenge.forohub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicoService {
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Topico crearTopico(DatosRegistroTopico datos) {
        if (datos.idAutor() != null && !usuarioRepository.existsById(datos.idAutor()))
            throw new RuntimeException("El usuario no existe");

        var autor = usuarioRepository.findById(datos.idAutor()).get();
        return new Topico(null, datos.titulo(), datos.mensaje(), datos.fechaCreacion(), datos.status(), autor);
    }
}
