package com.ms.voo.repository;

import com.ms.voo.model.Voo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VooRepository extends JpaRepository<Voo, String> {
}
