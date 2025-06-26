package com.dac.msreserva.util;

import com.dac.msreserva.model.EstadoReserva;
import com.dac.msreserva.model.EstadoReservaEnum;
import com.dac.msreserva.repository.EstadoReservaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(EstadoReservaRepository estadoReservaRepository) {
        return args -> {
            if (estadoReservaRepository.count() == 0) {
                for (EstadoReservaEnum estadoEnum : EstadoReservaEnum.values()) {
                    EstadoReserva estado = new EstadoReserva(
                            estadoEnum.getCodigo(),
                            estadoEnum.getCampo(), // exemplo: CANCELADA_VOO -> "CANCELADA VOO"
                            estadoEnum.name()     // exemplo: CANCELADA -> "CA"
                    );
                    estadoReservaRepository.save(estado);
                }
                System.out.println(">>> Estados de reserva cadastrados a partir do enum.");
            }
        };
    }
}
