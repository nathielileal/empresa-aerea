package com.dac.msreserva.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dac.msreserva.model.HistoricoReserva;

@Repository
public interface HistoricoRepository extends JpaRepository<HistoricoReserva, Long> {
}
