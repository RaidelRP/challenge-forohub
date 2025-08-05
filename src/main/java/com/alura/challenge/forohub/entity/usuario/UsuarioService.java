package com.alura.challenge.forohub.entity.usuario;

import com.alura.challenge.forohub.repository.TopicoRepository;
import com.alura.challenge.forohub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario crearUsuario(DatosRegistroUsuario datos) {
        var usuario = new Usuario(datos);
        usuarioRepository.save(usuario);
        return usuario;
    }

    public void eliminarUsuario(Long id) {
        var usuario = usuarioRepository.getReferenceById(id);
        var topicos = topicoRepository.findAllByIdAutor(id);
        topicos.stream().forEach(topico -> topicoRepository.delete(topico));
        usuarioRepository.delete(usuario);
    }

    public Page<DatosDetalleUsuario> listarUsuarios(Pageable paginacion) {
        return usuarioRepository.findAll(paginacion).map(DatosDetalleUsuario::new);
    }

    public Usuario detalleUsuario(Long id) {
        return usuarioRepository.getReferenceById(id);
    }

    public Usuario actualizarUsuario(DatosActualizacionUsuario datos) {
        var usuario = usuarioRepository.getReferenceById(datos.id());
        actualizarInformacion(usuario, datos);
        usuarioRepository.save(usuario);
        return usuario;
    }

    private void actualizarInformacion(Usuario usuario, DatosActualizacionUsuario datos) {
        if (datos.nombre() != null)
            usuario.setNombre(datos.nombre());
        if (datos.email() != null)
            usuario.setEmail(datos.email());
    }
}
