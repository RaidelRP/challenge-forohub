package com.alura.challenge.forohub.repository;

import com.alura.challenge.forohub.entity.topico.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
}
