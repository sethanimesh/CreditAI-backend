package com.example.CreditAI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
    private UserProfileRepository userProfileRepository;

	public UserProfile registerNewUser(UserRegistrationDTO userDTO) {
        // Check if user already exists (optional)
        if (userProfileRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username already taken");
        }

        if (userProfileRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        // Create a new user entity
        UserProfile user = new UserProfile();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword()); // Hash the password
        user.setStatus("ACTIVE"); // Default status

        // Save user to the database
        return userProfileRepository.save(user);
    }
}
