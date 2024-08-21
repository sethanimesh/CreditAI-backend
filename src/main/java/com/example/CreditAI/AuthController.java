package com.example.CreditAI;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserProfileRepository userProfileRepository;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(@Valid @RequestBody UserRegistrationDTO userDTO) {
        try {
            UserProfile registeredUser = userService.registerNewUser(userDTO);
            return ResponseEntity.ok(new RegisterResponse("User registered successfully", registeredUser.getUsername()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new RegisterResponse(e.getMessage(), null));
        }
    }
    
    @GetMapping("/register")
    public String func() {
    	return "Hello";
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        Optional<UserProfile> userOptional = userProfileRepository.findByUsername(loginRequest.getUsername());

        if (userOptional.isPresent()) {
            UserProfile user = userOptional.get();

            if (user.getPassword().equals(loginRequest.getPassword())) {
                // You might also want to return a JWT token or user details here
                return ResponseEntity.ok(new LoginResponse("Login successful", user.getUsername()));
            } else {
                return ResponseEntity.badRequest().body(new LoginResponse("Invalid password", null));
            }
        } else {
            return ResponseEntity.badRequest().body(new LoginResponse("Username not found", null));
        }
    }
}