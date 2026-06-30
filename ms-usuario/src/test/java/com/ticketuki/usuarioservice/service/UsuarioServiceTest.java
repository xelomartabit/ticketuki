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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private DireccionUsuarioRepository direccionRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private UsuarioRequestDTO requestValido() {
        DireccionDTO dir = new DireccionDTO("Av. Siempre Viva", "RM", "Ñuñoa", 742);
        return new UsuarioRequestDTO(
                "12.345.678-9", "Juan", "Carlos", "Pérez", "Soto",
                "juan.perez@mail.com", dir);
    }

    // ─────────────────────1 exito ─────────────────────────
    @Test
    void crearUsuario_conDatosValidos_devuelveUsuarioConId() {
        UsuarioRequestDTO req = requestValido();
        DireccionUsuario dirGuardada =
                new DireccionUsuario(10L, "Av. Siempre Viva", "RM", "Ñuñoa", 742);
        Usuario guardado = new Usuario(1L, "12.345.678-9", "Juan", "Carlos",
                "Pérez", "Soto", "juan.perez@mail.com", dirGuardada);

        when(direccionRepository.save(any(DireccionUsuario.class))).thenReturn(dirGuardada);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(guardado);

        UsuarioResponseDTO res = usuarioService.crearUsuario(req);

        assertThat(res.getId_usuario()).isEqualTo(1L);
        assertThat(res.getRun()).isEqualTo("12.345.678-9");
        assertThat(res.getEmail()).isEqualTo("juan.perez@mail.com");
        assertThat(res.getDireccion().getComuna()).isEqualTo("Ñuñoa");
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    // ────────────────────2: error run duplicado ────────────────────
    @Test
    void crearUsuario_conRunDuplicado_lanzaUsuarioDuplicadoException() {
        UsuarioRequestDTO req = requestValido();
        when(direccionRepository.save(any(DireccionUsuario.class)))
                .thenReturn(new DireccionUsuario(10L, "Av", "RM", "Ñuñoa", 742));
        when(usuarioRepository.save(any(Usuario.class)))
                .thenThrow(new DataIntegrityViolationException("UNIQUE run"));

        assertThatThrownBy(() -> usuarioService.crearUsuario(req))
                .isInstanceOf(UsuarioDuplicadoException.class)
                .hasMessage("Run ya existe!");
    }

    // ─────(ArgumentCaptoor) la entidad guardada se construye bien ───────
    @Test
    void crearUsuario_guardaUsuarioConDatosCorrectos() {
        UsuarioRequestDTO req = requestValido();
        DireccionUsuario dirGuardada =
                new DireccionUsuario(10L, "Av. Siempre Viva", "RM", "Ñuñoa", 742);

        when(direccionRepository.save(any(DireccionUsuario.class))).thenReturn(dirGuardada);
        // save devuelve un objeto distinto, el argumento capturado conserva su estado original
        when(usuarioRepository.save(any(Usuario.class)))
                .thenReturn(new Usuario(1L, "12.345.678-9", "Juan", "Carlos",
                        "Pérez", "Soto", "juan.perez@mail.com", dirGuardada));

        usuarioService.crearUsuario(req);

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).save(captor.capture());
        Usuario enviado = captor.getValue();

        assertThat(enviado.getId_usuario()).isNull();                 // sin id, lo asigna la BD
        assertThat(enviado.getRun()).isEqualTo("12.345.678-9");
        assertThat(enviado.getEmail()).isEqualTo("juan.perez@mail.com");
        assertThat(enviado.getDireccion()).isEqualTo(dirGuardada);    // direccion guardada enlazada
    }

    // ────────────3 error buscar inexistente ───────────────
    @Test
    void obtenerUsuarioPorId_inexistente_lanzaUsuarioNotFound() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.obtenerUsuarioPorId(99L))
                .isInstanceOf(UsuarioNotFoundException.class)
                .hasMessage("Usuario no encontrado con ID: 99");
    }

    // ─────────4 error eliminar + verificacion──────────
    @Test
    void eliminarUsuario_inexistente_lanzaExcepcionYNoElimina() {
        when(usuarioRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> usuarioService.eliminarUsuario(99L))
                .isInstanceOf(UsuarioNotFoundException.class)
                .hasMessage("Usuario no encontrado!");

        verify(usuarioRepository, never()).deleteById(anyLong());
    }
}
