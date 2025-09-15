package com.bahikhata.up_time.controller;

import com.bahikhata.up_time.config.JwtUtil;
import com.bahikhata.up_time.dto.AuthResponse;
import com.bahikhata.up_time.dto.LoginRequest;
import com.bahikhata.up_time.dto.SignupRequest;
import com.bahikhata.up_time.entity.User;
import com.bahikhata.up_time.service.interfaces.SessionService;
import com.bahikhata.up_time.service.interfaces.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    private final UserService userService;

    private final SessionService sessionService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, SessionService sessionService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.sessionService = sessionService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest){
        try{
            User user=userService.createUser(signupRequest);
            Map<String,Object> response=new HashMap<>();
            response.put("id",user.getId());
            response.put("name",user.getUsername());
            response.put("email",user.getEmail());
            response.put("firstName",user.getFirstName());
            response.put("lastName",user.getLastName());
            response.put("createdAt",user.getCreatedAt());
            response.put("message", "User registered successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch(Exception e){
            Map<String, String> error=new HashMap<>();
            error.put("error",e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
                                              HttpServletRequest request){
        try{
            Authentication authentication=authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            UserDetails userDetails=(UserDetails) authentication.getPrincipal();
            User user=userService.findByUsername(userDetails.getUsername());

            String accessToken=jwtUtil.generateToken(userDetails);
            String refreshToken=jwtUtil.generateRefreshToken(userDetails);
            sessionService.createSession(user,request);

            AuthResponse authResponse=new AuthResponse(accessToken,refreshToken);
            return ResponseEntity.ok(authResponse);
        }
        catch(Exception e){
            log.error("Authentication failed.",e);
            Map<String, String> error=new HashMap<>();
            error.put("message","Invalid username/password");
            return ResponseEntity.badRequest().body(error);
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(Authentication authentication) {
        try {
            String username = authentication.getName();
            User user = userService.findByUsername(username);

            // End the current session
            sessionService.endActiveSession(user);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Logged out successfully");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Logout failed");
            return ResponseEntity.badRequest().body(error);
        }
    }
}
