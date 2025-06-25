package com.ms.funcionario.util;

import com.ms.funcionario.model.Funcionario;
import com.ms.funcionario.repository.FuncionarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(FuncionarioRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                Funcionario func = new Funcionario();
                func.setCpf("90769281001");
                func.setEmail("func_pre@gmail.com");
                func.setNome("Funcionário Padrão");
                func.setTelefone(null); 
                func.setAtivo(true);

                repository.save(func);

                System.out.println("Funcionário cadastrado.");
            }
        };
    }
}
