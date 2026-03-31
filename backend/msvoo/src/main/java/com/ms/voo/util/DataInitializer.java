package com.ms.voo.util;

import com.ms.voo.model.Aeroporto;
import com.ms.voo.model.Voo;
import com.ms.voo.model.VooEstado;
import com.ms.voo.repository.AeroportoRepository;
import com.ms.voo.repository.VooEstadoRepository;
import com.ms.voo.repository.VooRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.OffsetDateTime;
import java.util.Optional;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(AeroportoRepository aeroportoRepository, VooRepository vooRepository, VooEstadoRepository vooEstadoRepository) {
        return args -> {
            if (vooEstadoRepository.count() == 0) {
                VooEstado confirmado = new VooEstado("CONFIRMADO", "Voo Confirmado");
                confirmado.setCodigo(1L);
                VooEstado cancelado = new VooEstado("CANCELADO", "Voo Cancelado");
                cancelado.setCodigo(2L);
                VooEstado realizado = new VooEstado("REALIZADO", "Voo Realizado");
                realizado.setCodigo(3L);

                vooEstadoRepository.save(confirmado);
                vooEstadoRepository.save(cancelado);
                vooEstadoRepository.save(realizado);

                System.out.println(">>> Estados de voo cadastrados.");
            }

            if (aeroportoRepository.count() == 0) {
                aeroportoRepository.save(new Aeroporto("GRU", "Aeroporto Internacional de São Paulo/Guarulhos", "Guarulhos", "SP"));
                aeroportoRepository.save(new Aeroporto("GIG", "Aeroporto Internacional do Rio de Janeiro/Galeão", "Rio de Janeiro", "RJ"));
                aeroportoRepository.save(new Aeroporto("CWB", "Aeroporto Internacional de Curitiba", "Curitiba", "PR"));
                aeroportoRepository.save(new Aeroporto("POA", "Aeroporto Internacional Salgado Filho", "Porto Alegre", "RS"));

                System.out.println(">>> Aeroportos cadastrados.");
            }

            VooEstado estadoConfirmado = vooEstadoRepository.findBySigla("CONFIRMADO")
                    .orElseThrow(() -> new RuntimeException("Estado CONFIRMADO não encontrado"));

            if (vooRepository.count() == 0) {
                Optional<Aeroporto> poa = aeroportoRepository.findById("POA");
                Optional<Aeroporto> cwb = aeroportoRepository.findById("CWB");
                Optional<Aeroporto> gig = aeroportoRepository.findById("GIG");

                if (poa.isPresent() && cwb.isPresent() && gig.isPresent()) {
                    Voo voo1 = new Voo(OffsetDateTime.parse("2025-08-10T10:30:00-03:00").toZonedDateTime(), poa.get(), cwb.get());
                    voo1.setEstado(estadoConfirmado);

                    Voo voo2 = new Voo(OffsetDateTime.parse("2025-09-11T09:30:00-03:00").toZonedDateTime(), cwb.get(), gig.get());
                    voo2.setEstado(estadoConfirmado);

                    Voo voo3 = new Voo(OffsetDateTime.parse("2025-10-12T08:30:00-03:00").toZonedDateTime(), cwb.get(), poa.get());
                    voo3.setEstado(estadoConfirmado);

                    vooRepository.save(voo1);
                    vooRepository.save(voo2);
                    vooRepository.save(voo3);
                    
                    System.out.println(">>> Voos cadastrados.");
                } else {
                    System.err.println("Erro: Alguns aeroportos necessários para os voos não foram encontrados.");
                }
            }
        };
    }
}
