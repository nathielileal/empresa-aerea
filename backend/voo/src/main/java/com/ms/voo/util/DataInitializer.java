package com.ms.voo.util;

import com.ms.voo.model.Aeroporto;
import com.ms.voo.model.Voo;
import com.ms.voo.repository.AeroportoRepository;
import com.ms.voo.repository.VooRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.OffsetDateTime;
import java.util.Optional;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(AeroportoRepository aeroportoRepository, VooRepository vooRepository) {
        return args -> {
            if (aeroportoRepository.count() == 0) {
                aeroportoRepository.save(new Aeroporto("GRU", "Aeroporto Internacional de São Paulo/Guarulhos", "Guarulhos", "SP"));
                aeroportoRepository.save(new Aeroporto("GIG", "Aeroporto Internacional do Rio de Janeiro/Galeão", "Rio de Janeiro", "RJ"));
                aeroportoRepository.save(new Aeroporto("CWB", "Aeroporto Internacional de Curitiba", "Curitiba", "PR"));
                aeroportoRepository.save(new Aeroporto("POA", "Aeroporto Internacional Salgado Filho", "Porto Alegre", "RS"));

                System.out.println(">>> Aeroportos cadastrados.");
            }

            if (vooRepository.count() == 0) {
                Optional<Aeroporto> poa = aeroportoRepository.findById("POA");
                Optional<Aeroporto> cwb = aeroportoRepository.findById("CWB");
                Optional<Aeroporto> gig = aeroportoRepository.findById("GIG");

                if (poa.isPresent() && cwb.isPresent() && gig.isPresent()) {
                    vooRepository.save(new Voo(OffsetDateTime.parse("2025-08-10T10:30:00-03:00").toLocalDateTime(), poa.get(), cwb.get()));
                    vooRepository.save(new Voo(OffsetDateTime.parse("2025-09-11T09:30:00-03:00").toLocalDateTime(), cwb.get(), gig.get()));
                    vooRepository.save(new Voo(OffsetDateTime.parse("2025-10-12T08:30:00-03:00").toLocalDateTime(), cwb.get(), poa.get()));

                    System.out.println(">>> Voos cadastrados.");
                } else {
                    System.err.println("Erro: Alguns aeroportos necessários para os voos não foram encontrados.");
                }
            }
        };
    }
}
