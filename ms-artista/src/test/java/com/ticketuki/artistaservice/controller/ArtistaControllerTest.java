package com.ticketuki.artistaservice.controller;

import com.ticketuki.artistaservice.dto.ArtistaResponseDTO;
import com.ticketuki.artistaservice.service.ArtistaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArtistaController.class)
class ArtistaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArtistaService artistaService;

    // ───────── (controller exito) get /artistas/{id} - 200 + body ─────────
    @Test
    void obtenerArtista_existente_devuelve200ConBody() throws Exception {
        ArtistaResponseDTO artista = new ArtistaResponseDTO(1L, "Mon Laferte", "Pop", "@monlaferte");
        when(artistaService.obtenerArtista(1L)).thenReturn(artista);

        mockMvc.perform(get("/artistas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_artista").value(1))
                .andExpect(jsonPath("$.nombre_artista").value("Mon Laferte"))
                .andExpect(jsonPath("$.genero_artista").value("Pop"));
    }

    // ───────────── validacion @Valid - 400 ─────────────
    @Test
    void crearArtista_conBodyInvalido_devuelve400() throws Exception {
        String bodyInvalido = "{}";   // faltan nombre, genero y redes (todos @NotBlank)

        mockMvc.perform(post("/artistas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyInvalido))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validacion fallida...."))
                .andExpect(jsonPath("$.status").value(400));
    }
}
