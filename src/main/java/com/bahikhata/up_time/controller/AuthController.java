package com.bahikhata.up_time.controller;

import com.bahikhata.up_time.service.interfaces.SessionService;
import com.bahikhata.up_time.service.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    private final UserService userService;

    private final SessionService sessionService;

    public AuthController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody Signup){}
    
    @PostMapping("/login")
    public ResponseEntity<?> login(){
        return ResponseEntity.ok("Login Successful");
    }
}
