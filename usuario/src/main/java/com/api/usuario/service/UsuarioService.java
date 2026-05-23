package com.api.usuario.service;

import com.api.usuario.dto.UsuarioRequestDTO;
import com.api.usuario.dto.UsuarioResponseDTO;
import com.api.usuario.exception.UsuarioDuplicadoException;
import com.api.usuario.exception.UsuarioNotFoundException;
import com.api.usuario.model.DireccionUsuario;
import com.api.usuario.model.Usuario;
import com.api.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO dto) {
        log.info("Creando usuario con RUN: {}", dto.getRun());

        boolean existe = usuarioRepository.findByRun(dto.getRun()).isPresent();
        if (existe) {
            log.warn("Intento de crear usuario con RUN duplicado: {}", dto.getRun());
            throw new UsuarioDuplicadoException("RUN ya existe en el sistema");
        }

        DireccionUsuario direccionUsuario = new DireccionUsuario();
        direccionUsuario.setCalle(dto.getDireccion().getCalle());
        direccionUsuario.setNum_calle(dto.getDireccion().getNum_calle());
        direccionUsuario.setComuna(dto.getDireccion().getComuna());
        direccionUsuario.setRegion(dto.getDireccion().getRegion());

        Usuario usuario = new Usuario();
        usuario.setRun(dto.getRun());
        usuario.setP_nombre(dto.getP_nombre());
        usuario.setS_nombre(dto.getS_nombre());
        usuario.setA_paterno(dto.getA_paterno());
        usuario.setA_materno(dto.getA_materno());
        usuario.setEmail(dto.getEmail());
        usuario.setId_direccion_usuario(direccionUsuario);

        Usuario saved = usuarioRepository.save(usuario);
        log.info("Usuario creado exitosamente con ID: {}", saved.getId_usuario());

        return UsuarioResponseDTO.mapUsuario(saved);
    }

    public List<UsuarioResponseDTO> listarUsuarios(){
        log.debug("Listando todos los usuarios");
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioResponseDTO::mapUsuario)
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO obtenerUsuarioPorId(Integer id) {
        log.debug("Obteniendo usuario con ID: {}", id);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Usuario no encontrado con ID: {}", id);
                    return new UsuarioNotFoundException("Usuario no encontrado");
                });
        return UsuarioResponseDTO.mapUsuario(usuario);
    }

    public UsuarioResponseDTO actualizarUsuario(Integer id, UsuarioRequestDTO dto) {
        log.info("Actualizando usuario con ID: {}", id);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Usuario no encontrado para actualizar: {}", id);
                    return new UsuarioNotFoundException("Usuario no encontrado");
                });

        usuario.setP_nombre(dto.getP_nombre());
        usuario.setS_nombre(dto.getS_nombre());
        usuario.setA_paterno(dto.getA_paterno());
        usuario.setA_materno(dto.getA_materno());

        if (usuario.getId_direccion_usuario() == null) {
            usuario.setId_direccion_usuario(new DireccionUsuario());
        }
        usuario.getId_direccion_usuario().setCalle(dto.getDireccion().getCalle());
        usuario.getId_direccion_usuario().setNum_calle(dto.getDireccion().getNum_calle());
        usuario.getId_direccion_usuario().setComuna(dto.getDireccion().getComuna());
        usuario.getId_direccion_usuario().setRegion(dto.getDireccion().getRegion());

        Usuario updated = usuarioRepository.save(usuario);
        log.info("Usuario actualizado exitosamente: {}", id);

        return UsuarioResponseDTO.mapUsuario(updated);
    }

    public void eliminarUsuario(Integer id) {
        log.info("Eliminando usuario con ID: {}", id);

        if (!usuarioRepository.existsById(id)) {
            log.warn("Usuario no encontrado para eliminar: {}", id);
            throw new UsuarioNotFoundException("Usuario no encontrado");
        }

        usuarioRepository.deleteById(id);
        log.info("Usuario eliminado exitosamente: {}", id);
    }
}