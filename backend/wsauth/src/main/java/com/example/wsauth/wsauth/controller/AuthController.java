package com.example.wsauth.wsauth.controller;

import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wsauth.wsauth.dto.LoginRequest;
import com.example.wsauth.wsauth.model.User;
import com.example.wsauth.wsauth.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private final SecretKey key = Keys
            .hmacShaKeyFor("segredo123segredo123segredo123segredo123".getBytes(StandardCharsets.UTF_8));

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        Optional<User> opt = userRepository.findByEmail(req.getEmail());
        if (opt.isEmpty())
            return ResponseEntity.status(401).body("Usuário não encontrado");

        User user = opt.get();
        if (!passwordEncoder.matches(req.getSenha(), user.getSenha())) {
            return ResponseEntity.status(401).body("Senha inválida");
        }

        String token = Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .signWith(key)
                .compact();

        return ResponseEntity.ok(Map.of("token", token));
    }
}