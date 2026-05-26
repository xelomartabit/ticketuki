package com.ticketuki.recintoservice.repository;

import com.ticketuki.recintoservice.model.Recinto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecintoRepository extends JpaRepository<Recinto, Long> {
}
