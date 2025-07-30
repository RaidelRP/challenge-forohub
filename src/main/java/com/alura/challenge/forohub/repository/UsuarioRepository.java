package com.alura.challenge.forohub.repository;

import com.alura.challenge.forohub.entity.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String subject);

//    Page<Usuario> findAll(Pageable paginacion);
}
