package com.api.usuario.controller;

import com.api.usuario.dto.UsuarioRequestDTO;
import com.api.usuario.dto.UsuarioResponseDTO;
import com.api.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@Slf4j

public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crear(@Valid @RequestBody UsuarioRequestDTO dto) {
        log.info("POST /api/usuarios - Creando nuevo usuario");
        UsuarioResponseDTO response = usuarioService.crearUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtener(@PathVariable Integer id) {
        log.info("GET /api/usuarios/{} - Obteniendo usuario", id);
        return ResponseEntity.ok(usuarioService.obtenerUsuarioPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        log.info("GET /api/usuarios - Listando usuarios");
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(@PathVariable Integer id,
                                                         @Valid @RequestBody UsuarioRequestDTO dto) {
        log.info("PUT /api/usuarios/{} - Actualizando usuario", id);
        return ResponseEntity.ok(usuarioService.actualizarUsuario(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("DELETE /api/usuarios/{} - Eliminando usuario", id);
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }













}
