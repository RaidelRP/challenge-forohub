package com.alura.challenge.forohub.controller;

import com.alura.challenge.forohub.entity.usuario.DatosDetalleUsuario;
import com.alura.challenge.forohub.entity.usuario.DatosRegistroUsuario;
import com.alura.challenge.forohub.entity.usuario.UsuarioService;
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
//    @Autowired
//    private UsuarioRepository repository;

    @Autowired
    private UsuarioService usuarioService;

    @Transactional
    @PostMapping("/registrar")
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroUsuario datos, UriComponentsBuilder builder) {
        var usuario = usuarioService.crearUsuario(datos);
        var uri = builder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosDetalleUsuario(usuario));
    }

    @GetMapping
    public ResponseEntity<Page<DatosDetalleUsuario>> listar(@PageableDefault(size = 10, sort = {"nombre"}) Pageable paginacion) {
        var page = usuarioService.listarUsuarios(paginacion);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detallar(@PathVariable Long id) {
        var usuario = usuarioService.detalleUsuario(id);
        return ResponseEntity.ok(new DatosDetalleUsuario(usuario));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity eliminar(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
