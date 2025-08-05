package com.alura.challenge.forohub.entity.topico;

import com.alura.challenge.forohub.entity.usuario.Usuario;
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
        var autor = getAutor(datos.idAutor());
        var topico = new Topico(null, datos.titulo(), datos.mensaje(), datos.fechaCreacion(), datos.status(), autor);
        topicoRepository.save(topico);
        return topico;
    }

    private Usuario getAutor(Long idAutor) {
        if (idAutor != null && !usuarioRepository.existsById(idAutor))
            throw new ValidacionException("El usuario no existe");

        var autor = usuarioRepository.findById(idAutor).get();
        return autor;
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

    public Topico actualizarTopico(DatosActualizacionTopico datos) {
        var topico = topicoRepository.getReferenceById(datos.id());
        actualizarInformacion(topico, datos);
        topicoRepository.save(topico);
        return topico;
    }

    public Topico actualizarEstado(DatosActualizacionEstadoTopico datos) {
        var topico = topicoRepository.getReferenceById(datos.id());
        if (datos.status() != null)
            topico.setStatus(datos.status());
        topicoRepository.save(topico);
        return topico;
    }

    private void actualizarInformacion(Topico topico, DatosActualizacionTopico datos) {
        if (datos.titulo() != null)
            topico.setTitulo(datos.titulo());
        if (datos.mensaje() != null)
            topico.setMensaje(datos.mensaje());
        if (datos.fechaCreacion() != null)
            topico.setFechaCreacion(datos.fechaCreacion());
        if (datos.status() != null)
            topico.setStatus(datos.status());
        if (datos.idAutor() != null) {
            var autor = getAutor(datos.idAutor());
            topico.setAutor(autor);
        }
    }
}
