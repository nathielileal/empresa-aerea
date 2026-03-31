package com.dac.msreserva.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dac.msreserva.model.Reserva;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Reserva, String> {

    @Query("SELECT r FROM Reserva r WHERE r.codigo_voo = ?1")
    List<Reserva> findByCodigoVoo(String codigo_voo);
}
