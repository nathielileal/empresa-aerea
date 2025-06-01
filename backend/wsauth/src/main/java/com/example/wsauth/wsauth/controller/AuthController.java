package com.example.wsauth.wsauth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wsauth.wsauth.DTO.LoginDTO;
import com.example.wsauth.wsauth.DTO.LoginResponseDTO;
import com.example.wsauth.wsauth.model.User;

import com.example.wsauth.wsauth.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO req) {
        try {
            LoginResponseDTO response = authService.login(req);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao realizar login");
        }
    }
    // public ResponseEntity<?> login(@RequestBody LoginDTO req) {
    // Optional<User> opt = userRepository.findByEmail(req.getEmail());
    // if (opt.isEmpty())
    // return ResponseEntity.status(401).body("Usuário não encontrado");

    // User user = opt.get();
    // if (!passwordEncoder.matches(req.getSenha(), user.getSenha())) {
    // return ResponseEntity.status(401).body("Senha inválida");
    // }

    // String token = Jwts.builder()
    // .setSubject(user.getEmail())
    // .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
    // .signWith(key)
    // .compact();

    // return ResponseEntity.ok(Map.of(
    // "token", token,
    // "perfil", user.getPerfil()));
    // }

    @GetMapping("/usuarios")
    public ResponseEntity<List<User>> listarUsuarios() {
        List<User> usuarios = authService.getAllUsers();
        return ResponseEntity.ok(usuarios);
    }
}