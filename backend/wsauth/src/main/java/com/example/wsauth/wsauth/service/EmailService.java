package com.example.wsauth.wsauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired private JavaMailSender mailSender;

    public void enviarSenha(String to, String senha) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Sua senha de acesso");
        message.setText("Sua senha é: " + senha);
        mailSender.send(message);
    }
}
