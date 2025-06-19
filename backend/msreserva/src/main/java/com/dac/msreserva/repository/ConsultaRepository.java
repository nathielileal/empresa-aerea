package com.dac.msreserva.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dac.msreserva.model.ReservaConsulta;

import jakarta.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<ReservaConsulta, String> {

    @Query("SELECT r FROM ReservaConsulta r WHERE r.codigo_cliente = :codigo_cliente")
    List<ReservaConsulta> buscarPorCodigoCliente(@Param("codigo_cliente") Long codigo_cliente);
}