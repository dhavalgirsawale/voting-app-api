package com.example.votingapp;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.votingapp.model.User;
import com.example.votingapp.repository.UserRepository;


@SpringBootApplication
public class VotingappApplication {

	public static void main(String[] args) {
		SpringApplication.run(VotingappApplication.class, args);
	}
	
	@Bean
    public CommandLineRunner migratePasswords(
            UserRepository userRepository, 
            PasswordEncoder passwordEncoder) {
        return args -> {
            List<User> users = userRepository.findAll();
            for (User user : users) {
                // Check if password is not already encrypted
                if (!user.getPassword().startsWith("$2a$")) {
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    userRepository.save(user);
                }
            }
        };
    }

}
