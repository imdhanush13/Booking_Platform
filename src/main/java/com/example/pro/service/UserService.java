package com.example.pro.service;

import com.example.pro.model.User;
import com.example.pro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository urepo;

    public ResponseEntity<?> registerUser(User user) {
        if (urepo.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body("Email already registered");
        }
        if (urepo.findByMobile(user.getMobile()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body("Mobile number already registered");
        }
        User savedUser = urepo.save(user);
        return ResponseEntity.ok(savedUser);
    }

    public ResponseEntity<?> login(String emailOrMobile, String password) {
        Optional<User> user = urepo.findByEmail(emailOrMobile);
        if (user.isEmpty()) {
            user = urepo.findByMobile(emailOrMobile);
        }
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity
                .status(401)
                .body("Invalid email/mobile or password");
    }
}
