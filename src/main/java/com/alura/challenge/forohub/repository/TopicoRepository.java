package com.alura.challenge.forohub.repository;

import com.alura.challenge.forohub.entity.topico.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Page<Topico> findAll(Pageable paginacion);

    @Query("SELECT t FROM Topico t where t.autor.id =:idAutor")
    List<Topico> findAllByIdAutor(Long idAutor);
}
