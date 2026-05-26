package com.ticketuki.usuarioservice.service;

import com.ticketuki.usuarioservice.dto.DireccionDTO;
import com.ticketuki.usuarioservice.dto.UsuarioRequestDTO;
import com.ticketuki.usuarioservice.dto.UsuarioResponseDTO;
import com.ticketuki.usuarioservice.exception.UsuarioDuplicadoException;
import com.ticketuki.usuarioservice.exception.UsuarioNotFoundException;
import com.ticketuki.usuarioservice.model.DireccionUsuario;
import com.ticketuki.usuarioservice.model.Usuario;
import com.ticketuki.usuarioservice.repository.DireccionUsuarioRepository;
import com.ticketuki.usuarioservice.repository.UsuarioRepository;
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
    private final DireccionUsuarioRepository direccionRepository;

    private UsuarioResponseDTO mapToResponse(Usuario usuario) {
        DireccionDTO direccionDTO = null;
        if (usuario.getDireccion() != null) {
            direccionDTO = new DireccionDTO(
                usuario.getDireccion().getCalle(),
                usuario.getDireccion().getRegion(),
                usuario.getDireccion().getComuna(),
                usuario.getDireccion().getNum_calle()
            );
        }
        return new UsuarioResponseDTO(
            usuario.getId_usuario(),
            usuario.getRun(),
            usuario.getP_nombre(),
            usuario.getS_nombre(),
            usuario.getA_materno(),
            usuario.getA_paterno(),
            usuario.getEmail(),
            direccionDTO
        );
    }

    public List<UsuarioResponseDTO> listarUsuarios() {
        log.info("Obteniendo todos los usuarios");
        return usuarioRepository.findAll()
            .stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    public UsuarioResponseDTO obtenerUsuarioPorId(Long id) {
        log.info("Obteniendo usuario por id: {}", id);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Usuario no encontrado con ID: {}", id);
                    return new UsuarioNotFoundException("Usuario no encontrado con ID: " + id);
                });
        return mapToResponse(usuario);
    }

    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO usuarioDTO) {
        log.info("Creando nuevo usuario con run: {}", usuarioDTO.getRun());

        boolean existe = usuarioRepository.findByRun(usuarioDTO.getRun()).isPresent();
        if (existe) {
            log.warn("Intento de crear usuario con RUN duplicado: {}", usuarioDTO.getRun());
            throw new UsuarioDuplicadoException("RUN ya existe en el sistema");
        }

        DireccionUsuario direccion = null;
        if (usuarioDTO.getDireccion() != null) {
            direccion = new DireccionUsuario(
                null,
                usuarioDTO.getDireccion().getCalle(),
                usuarioDTO.getDireccion().getRegion(),
                usuarioDTO.getDireccion().getComuna(),
                usuarioDTO.getDireccion().getNum_calle()
            );
            direccion = direccionRepository.save(direccion);
        }

        Usuario usuario = new Usuario(
            null,
            usuarioDTO.getRun(),
            usuarioDTO.getP_nombre(),
            usuarioDTO.getS_nombre(),
            usuarioDTO.getA_materno(),
            usuarioDTO.getA_paterno(),
            usuarioDTO.getEmail(),
            direccion
        );

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        log.info("Usuario creado exitosamente con ID: {}", usuarioGuardado.getId_usuario());

        return mapToResponse(usuarioGuardado);
    }

    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO usuarioDTO) {
        log.info("Actualizando usuario con id: {}", id);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Usuario no encontrado para actualizar: {}", id);
                    return new UsuarioNotFoundException("Usuario no encontrado con ID: " + id);
                });

        usuario.setRun(usuarioDTO.getRun());
        usuario.setP_nombre(usuarioDTO.getP_nombre());
        usuario.setS_nombre(usuarioDTO.getS_nombre());
        usuario.setA_materno(usuarioDTO.getA_materno());
        usuario.setA_paterno(usuarioDTO.getA_paterno());
        usuario.setEmail(usuarioDTO.getEmail());

        if (usuarioDTO.getDireccion() != null && usuario.getDireccion() != null) {
            usuario.getDireccion().setCalle(usuarioDTO.getDireccion().getCalle());
            usuario.getDireccion().setRegion(usuarioDTO.getDireccion().getRegion());
            usuario.getDireccion().setComuna(usuarioDTO.getDireccion().getComuna());
            usuario.getDireccion().setNum_calle(usuarioDTO.getDireccion().getNum_calle());
        }

        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        log.info("Usuario actualizado exitosamente: {}", id);
        return mapToResponse(usuarioActualizado);
    }

    public void eliminarUsuario(Long id) {
        log.info("Eliminando usuario con ID: {}", id);

        if (!usuarioRepository.existsById(id)) {
            log.warn("Usuario no encontrado para eliminar: {}", id);
            throw new UsuarioNotFoundException("Usuario no encontrado");
        }

        usuarioRepository.deleteById(id);
        log.info("Usuario eliminado exitosamente: {}", id);
    }
}
