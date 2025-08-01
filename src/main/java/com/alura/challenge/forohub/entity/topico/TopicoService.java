package com.alura.challenge.forohub.entity.topico;

import com.alura.challenge.forohub.infra.exceptions.ValidacionException;
import com.alura.challenge.forohub.repository.TopicoRepository;
import com.alura.challenge.forohub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TopicoService {
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Topico crearTopico(DatosRegistroTopico datos) {
        if (datos.idAutor() != null && !usuarioRepository.existsById(datos.idAutor()))
            throw new ValidacionException("El usuario no existe");

        var autor = usuarioRepository.findById(datos.idAutor()).get();
        var topico = new Topico(null, datos.titulo(), datos.mensaje(), datos.fechaCreacion(), datos.status(), autor);
        topicoRepository.save(topico);
        return topico;
    }

    public void eliminarTopico(Long id) {
        var topico = topicoRepository.getReferenceById(id);
        topicoRepository.delete(topico);
    }

    public Page<DatosDetalleTopico> listarTopicos(Pageable paginacion) {
        return topicoRepository.findAll(paginacion).map(DatosDetalleTopico::new);
    }

    public Topico detalleTopico(Long id) {
        return topicoRepository.getReferenceById(id);
    }

    public Page<DatosDetalleTopico> listar(Pageable paginacion) {
        return topicoRepository.findAll(paginacion).map(DatosDetalleTopico::new);
    }
}
