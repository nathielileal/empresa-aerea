package com.ms.voo.repository;

import com.ms.voo.model.VooEstado;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VooEstadoRepository extends JpaRepository<VooEstado, Long> {
    Optional<VooEstado> findBySigla(String sigla);
}
