package com.dac.msreserva.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dac.msreserva.model.ReservaConsulta;
import com.dac.msreserva.model.ReservaConsultaId;

import jakarta.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<ReservaConsulta, ReservaConsultaId> {

    @Query(
        value = """
            SELECT 
                r.codigo,
                r.codigo_voo,
                r.codigo_cliente,
                r.estado,
                r.data,
                ARRAY_AGG(r.poltrona) AS poltronas,
                r.quantidade_milhas
            FROM reserva_access.reserva r
            GROUP BY r.codigo, r.codigo_voo, r.codigo_cliente, r.estado, r.data, r.quantidade_milhas
        """,
        nativeQuery = true
    )
    List<ReservaConsulta> findReservas();

    @Query(
        value = """
            SELECT 
                r.codigo,
                r.codigo_voo,
                r.codigo_cliente,
                r.estado,
                r.data,
                ARRAY_AGG(r.poltrona) AS poltronas,
                r.quantidade_milhas
            FROM reserva_access.reserva r
            WHERE r.codigo_cliente = :codigo_cliente
            GROUP BY r.codigo, r.codigo_voo, r.codigo_cliente, r.estado, r.data, r.quantidade_milhas
        """,
        nativeQuery = true
    )
    List<ReservaConsulta> findReservasByCodigoCliente(@Param("codigo_cliente") Long codigo_cliente);

    @Query(
        value = """
            SELECT 
                r.codigo,
                r.codigo_voo,
                r.codigo_cliente,
                r.estado,
                r.data,
                r.quantidade_milhas
            FROM reserva_access.reserva r
            WHERE r.codigo = :codigo
            GROUP BY r.codigo, r.codigo_cliente, r.codigo_voo, r.estado, r.data, r.quantidade_milhas
        """,
        nativeQuery = true
    )
    ReservaConsulta findReservaByCodigo(@Param("codigo") String codigo);

    @Transactional
    @Modifying
    @Query(
        value = """
            INSERT INTO 
                reserva_access.reserva 
                (codigo, codigo_cliente, codigo_voo, estado, data, poltrona, quantidade_milhas) 
            VALUES
                (:codigo, :codigo_cliente, :codigo_voo, :estado, :data, :poltrona, :quantidade_milhas);
        """,
        nativeQuery = true
    )
    void save(
        @Param("codigo") String codigo,
        @Param("codigo_cliente") Long codigo_cliente,
        @Param("codigo_voo") Long codigo_voo,
        @Param("estado") String estado,
        @Param("data") ZonedDateTime data,
        @Param("quantidade_milhas") Double quantidade_milhas
    );

    @Transactional
    @Modifying
    @Query(
        value = """
            UPDATE reserva_access.reserva
            SET estado = :estado, data = :data
            WHERE codigo = :codigo
        """,
        nativeQuery = true
    )
    void update(
        @Param("estado") String estado,
        @Param("data") ZonedDateTime data,
        @Param("codigo") String codigo
    );
}
