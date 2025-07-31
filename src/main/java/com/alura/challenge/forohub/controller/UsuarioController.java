package com.alura.challenge.forohub.controller;

import com.alura.challenge.forohub.entity.usuario.DatosDetalleUsuario;
import com.alura.challenge.forohub.entity.usuario.DatosRegistroUsuario;
import com.alura.challenge.forohub.entity.usuario.Usuario;
import com.alura.challenge.forohub.repository.UsuarioRepository;
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
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository repository;

    @Transactional
    @PostMapping("/registrar")
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroUsuario datos, UriComponentsBuilder builder) {
        var usuario = new Usuario(datos);
        repository.save(usuario);
        var uri = builder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosDetalleUsuario(usuario));
    }

    @GetMapping
    public ResponseEntity<Page<DatosDetalleUsuario>> listar(@PageableDefault(size = 10, sort = {"nombre"}) Pageable paginacion) {
        var page = repository.findAll(paginacion).map(DatosDetalleUsuario::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detallar(@PathVariable Long id) {
        var usuario = repository.getReferenceById(id);
        return ResponseEntity.ok(new DatosDetalleUsuario(usuario));
    }
}
