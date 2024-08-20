package com.example.CreditAI;

import java.util.Optional;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long>{
	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);
}
