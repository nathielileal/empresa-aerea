// package com.example.wsauth.wsauth.rest;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.wsauth.wsauth.model.Login;
// import com.example.wsauth.wsauth.model.User;

// @CrossOrigin
// @RestController
// public class AuthREST {

//     @PostMapping("/login")
//     ResponseEntity<User> login(@RequestBody Login login) {
//         // BD para verificar o login/senha
//         if (login.getLogin().equals(login.getPassword())) {
//             User usu = new User(1, login.getLogin(), login.getLogin(), "XXX", "ADMIN");
//             return ResponseEntity.ok().body(usu);
//         } else {
//             return ResponseEntity.status(401).build();
//         }
//     }
// }
