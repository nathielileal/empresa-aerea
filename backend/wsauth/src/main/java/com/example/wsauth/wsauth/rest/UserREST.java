// package com.example.wsauth.wsauth.rest;

// import java.util.ArrayList;
// import java.util.List;

// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.wsauth.wsauth.model.User;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;

// @CrossOrigin
// @RestController
// public class UserREST {
//     public static List<User> list = new ArrayList<>();

//     @GetMapping("/users")
//     public List<User> getAllUsers() {
//         return list;
//     }

//     static {
//         list.add(new User(1, "administr", "admin", "admin", "ADMIN"));
//         list.add(new User(2, "gerent", "gerente", "gerente", "GERENTE"));
//         list.add(new User(3, "funcion", "func", "func", "FUNC"));
//     }
// }
