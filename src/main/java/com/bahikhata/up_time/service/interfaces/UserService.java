package com.bahikhata.up_time.service.interfaces;

import com.bahikhata.up_time.dto.SignupRequest;
import com.bahikhata.up_time.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User createUser(SignupRequest signupRequest);
    User findByUsername(String username);
    User findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
