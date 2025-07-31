package com.alura.challenge.forohub.controller;

import com.alura.challenge.forohub.entity.topico.DatosDetalleTopico;
import com.alura.challenge.forohub.entity.topico.DatosRegistroTopico;
import com.alura.challenge.forohub.entity.topico.TopicoService;
import com.alura.challenge.forohub.repository.TopicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    @Autowired
    private TopicoRepository repository;

    @Autowired
    private TopicoService topicoService;

    @Transactional
    @PostMapping
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroTopico datos, UriComponentsBuilder builder) {
        var topico = topicoService.crearTopico(datos);
        repository.save(topico);
        var uri = builder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosDetalleTopico(topico));
    }


}
