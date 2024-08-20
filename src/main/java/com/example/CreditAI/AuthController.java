package com.example.CreditAI;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserProfileRepository userProfileRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegistrationDTO userDTO) {
        try {
            UserProfile registeredUser = userService.registerNewUser(userDTO);
            return ResponseEntity.ok("User registered successfully with username: " + registeredUser.getUsername());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/register")
    public String func() {
    	return "Hello";
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        Optional<UserProfile> userOptional = userProfileRepository.findByUsername(loginRequest.getUsername());

        if (userOptional.isPresent()) {
            UserProfile user = userOptional.get();

            if (user.getPassword().equals(loginRequest.getPassword())) {
                return ResponseEntity.ok("Login successful. Welcome, " + user.getUsername() + "!");
            } else {
                return ResponseEntity.badRequest().body("Invalid password. Please try again.");
            }
        } else {
            return ResponseEntity.badRequest().body("Username not found. Please try again.");
        }
    }
}