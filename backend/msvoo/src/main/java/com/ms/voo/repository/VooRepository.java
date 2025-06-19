package com.ms.voo.repository;

import com.ms.voo.model.Voo;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VooRepository extends JpaRepository<Voo, String> {
}
