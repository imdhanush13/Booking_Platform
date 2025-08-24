package com.example.pro.controller;

import com.example.pro.model.User;
import com.example.pro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        return service.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String emailOrMobile,
                                   @RequestParam String password) {
        return service.login(emailOrMobile, password);
    }
}
