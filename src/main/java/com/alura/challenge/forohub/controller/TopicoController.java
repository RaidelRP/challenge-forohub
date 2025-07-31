package com.alura.challenge.forohub.controller;

import com.alura.challenge.forohub.entity.topico.DatosDetalleTopico;
import com.alura.challenge.forohub.entity.topico.DatosRegistroTopico;
import com.alura.challenge.forohub.entity.topico.TopicoService;
import com.alura.challenge.forohub.repository.TopicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping
    public ResponseEntity<Page<DatosDetalleTopico>> listar(@PageableDefault(size = 10, sort = {"nombre"}) Pageable paginacion) {
        var page = repository.findAll(paginacion).map(DatosDetalleTopico::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detallar(@PathVariable Long id) {
        var topico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }
}
