package com.ticketuki.authservice.controller;

import com.ticketuki.authservice.dto.LoginRequestDTO;
import com.ticketuki.authservice.dto.LoginResponseDTO;
import com.ticketuki.authservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        log.info("POST /auth/login - Autenticando usuario");
        return ResponseEntity.ok(authService.login(dto));
    }
}
