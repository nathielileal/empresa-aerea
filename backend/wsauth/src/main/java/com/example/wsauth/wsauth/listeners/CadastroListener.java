package com.example.wsauth.wsauth.listeners;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.wsauth.wsauth.DTO.CadastroDTO;
import com.example.wsauth.wsauth.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CadastroListener {
    @Autowired
    private AuthService authService;
    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "autocadastro.autenticacao")
    public void autoCadastro(String cadastroJson) {
        try {
            System.out.println("Fila para usuario escutada");
            CadastroDTO cadastroDTO = objectMapper.readValue(cadastroJson, CadastroDTO.class);

            CadastroDTO createdCadastro = authService.cadastro(cadastroDTO);

            if (createdCadastro == null || createdCadastro.getEmail() == null) {
                System.err.println("Cadastro retornou nulo ou inválido.");
                throw new AmqpRejectAndDontRequeueException("Cadastro inválido");
            }

            System.out.println("Usuário criado com sucesso: " + createdCadastro.getEmail());

        } catch (Exception e) {
            System.err.println("Erro inesperado ao processar autocadastro: " + e.getMessage());
            throw new AmqpRejectAndDontRequeueException("Erro ao processar mensagem", e);
        }
    }

}
