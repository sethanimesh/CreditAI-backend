package com.example.CreditAI;

import java.util.Optional;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long>{
	Optional<UserProfile> findByUsername(String username);

	Optional<UserProfile> findByEmail(String email);
}
