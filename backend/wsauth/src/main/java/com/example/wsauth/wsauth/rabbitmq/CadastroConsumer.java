package com.example.wsauth.wsauth.rabbitmq;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.wsauth.wsauth.model.User;
import com.example.wsauth.wsauth.repository.UserRepository;
import com.example.wsauth.wsauth.service.EmailService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CadastroConsumer {
    @Autowired private UserRepository userRepository;
    @Autowired private EmailService emailService;
    @Autowired private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "${rabbitmq.queue.cadastro-iniciado}")
    public void consumirMensagem(String mensagemJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(mensagemJson);

            String nome = node.get("nome").asText();
            String email = node.get("email").asText();
            String perfil= node.get("perfil").asText();

            String senha = String.valueOf(new Random().nextInt(9000) + 1000);
            String senhaHash = new BCryptPasswordEncoder().encode(senha);

            User user = new User();
            user.setPerfil(perfil);
            user.setNome(nome);
            user.setEmail(email);
            user.setSenha(senhaHash);
            userRepository.save(user);

            emailService.enviarSenha(email, senha);

            // Criando a resposta como JSON
            Map<String, String> resposta = new HashMap<>();
            resposta.put("email", email);
            resposta.put("status", "ok");

            String respostaJson = mapper.writeValueAsString(resposta);
            rabbitTemplate.convertAndSend("cliente.exchange", "cliente.routingkey", respostaJson);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
