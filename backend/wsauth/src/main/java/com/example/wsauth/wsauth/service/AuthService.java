package com.example.wsauth.wsauth.service;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

import javax.crypto.SecretKey;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.wsauth.wsauth.DTO.CadastroDTO;
import com.example.wsauth.wsauth.DTO.ClienteDTO;
import com.example.wsauth.wsauth.DTO.FuncionarioDTO;
import com.example.wsauth.wsauth.DTO.LoginDTO;
import com.example.wsauth.wsauth.DTO.LoginResponseDTO;
import com.example.wsauth.wsauth.model.User;
import com.example.wsauth.wsauth.repository.UserRepository;
import com.google.gson.Gson;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ModelMapper mapper;
    @Qualifier("sagaLogin")
    @Autowired
    private DirectExchange exchange;

    private final SecretKey key = Keys
            .hmacShaKeyFor("segredo123segredo123segredo123segredo123".getBytes(StandardCharsets.UTF_8));

    public CadastroDTO cadastro(CadastroDTO cadastroDTO) {
        try {
            // Gera uma senha aleatória de 4 dígitos
            String senha = String.valueOf(new Random().nextInt(9000) + 1000);
            String senhaHash = new BCryptPasswordEncoder().encode(senha);

            // Cria e preenche entidade User
            User user = new User();
            user.setPerfil(cadastroDTO.getPerfil());
            user.setNome(cadastroDTO.getNome());
            user.setEmail(cadastroDTO.getEmail());
            user.setSenha(senhaHash);

            // Salva o usuário no banco
            userRepository.save(user);

            // Envia e-mail com a senha
            emailService.enviarSenha(user.getEmail(), senha);

            // Retorna DTO criado
            return mapper.map(user, CadastroDTO.class);

        } catch (Exception e) {
            System.err.println("Erro ao cadastrar usuário: " + e.getMessage());
            throw new RuntimeException("Erro ao cadastrar usuário", e);
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public LoginResponseDTO login(LoginDTO req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(req.getSenha(), user.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }

        String token = Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .signWith(key)
                .compact();

        String routingKey = user.getPerfil().equalsIgnoreCase("CLIENTE") ? "cliente" : "funcionario";

        String responseString = (String) rabbitTemplate.convertSendAndReceive(exchange.getName(), routingKey,
                user.getEmail().toString());

        Object dadosUsuario = processarDadosUsuario(responseString, user.getPerfil());

        return new LoginResponseDTO(token, "bearer", user.getPerfil(), dadosUsuario);
    }

    private Object processarDadosUsuario(String json, String perfil) {
        Gson gson = new Gson();

        if (perfil.equalsIgnoreCase("CLIENTE")) {
            return gson.fromJson(json, ClienteDTO.class);
        } else {
            return gson.fromJson(json, FuncionarioDTO.class);
        }
    }
}