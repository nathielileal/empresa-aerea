package com.ms.voo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.ms.voo.dto.VooDTO;
import com.ms.voo.model.Voo;

@SpringBootApplication
public class VooApplication {

    public static void main(String[] args) {
        SpringApplication.run(VooApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(Voo.class, VooDTO.class).addMappings(mapper -> {
            mapper.map(Voo::getAeroportoOrigem, VooDTO::setAeroporto_origem);
            mapper.map(Voo::getAeroportoDestino, VooDTO::setAeroporto_destino);
        });

        return modelMapper;
    }

}
