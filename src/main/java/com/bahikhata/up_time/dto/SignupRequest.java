package com.bahikhata.up_time.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    @NotBlank( message = "Username is required")
    @Size(min=3, max=20, message = "Username must be between 3 to 20 characters")
    private String username;

    @NotBlank(message="Email is required")
    @Email(message="Please provide a valid email")
    private String email;

    @NotBlank(message="Password is required")
    @Size(min=6, max=40, message="Password must be between 6 to 40 characters")
    private String password;

    @NotBlank(message="First Name is required")
    private String firstName;

    @NotBlank(message="Last Name is required")
    private String lastName;
}
