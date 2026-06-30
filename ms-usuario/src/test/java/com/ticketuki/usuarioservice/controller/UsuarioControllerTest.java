package com.ticketuki.usuarioservice.controller;

import com.ticketuki.usuarioservice.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    // ──────────5: validacion @Valid - 400 con mensajee ───────────
    @Test
    void crearUsuario_conBodyInvalido_devuelve400() throws Exception {
        String bodyInvalido = """
            {
              "run": "123",
              "p_nombre": "",
              "a_materno": "Pérez",
              "a_paterno": "Soto",
              "email": "no-es-email"
            }
            """;

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyInvalido))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validación fallida"))
                .andExpect(jsonPath("$.status").value(400));
    }
}
