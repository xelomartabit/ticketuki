package com.ticketuki.usuarioservice.repository;

import com.ticketuki.usuarioservice.model.DireccionUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DireccionUsuarioRepository extends JpaRepository<DireccionUsuario, Long> {
}
