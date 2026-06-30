package com.ticketuki.ticketservice.controller;

import com.ticketuki.ticketservice.service.TicketService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TicketController.class)
class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    // ────────────6: validacion @Valid - 400 ─────────────
    @Test
    void crearTicket_conBodyInvalido_devuelve400() throws Exception {
        // faltan num_asiento, nombre_titular, run_titular, evento y sector...
        String bodyInvalido = "{}";

        mockMvc.perform(post("/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyInvalido))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validacion fallida..."))
                .andExpect(jsonPath("$.status").value(400));
    }
}
