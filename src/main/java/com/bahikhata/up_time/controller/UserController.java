package com.bahikhata.up_time.controller;

import com.bahikhata.up_time.entity.User;
import com.bahikhata.up_time.repository.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserRepository userRepo;

    public UserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping("/add")
    public User addUser(@RequestBody User user) {
        return userRepo.save(user);
    }
}

