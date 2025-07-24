package com.example.votingapp.controller;

import com.example.votingapp.model.User;
import com.example.votingapp.service.AuthService;
import com.example.votingapp.service.VotingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private VotingService votingService;
    
    @PostMapping("/login/user")
    public ResponseEntity<?> loginUser(
            @RequestParam String userId,
            @RequestParam String password) {
        User user = authService.authenticateUser(userId, password);
        if (user != null  && user.isCanVote()) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().body("Invalid credentials or voting access denied");
    }
    
    @PostMapping("/login/admin")
    public ResponseEntity<?> loginAdmin(
            @RequestParam String userId,
            @RequestParam String password) {
        User user = authService.authenticateAdmin(userId, password);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().body("Invalid admin credentials");
    }
    
 // New endpoint to check if user has voted in specific session
    @GetMapping("/check-voted/{sessionId}")
    public ResponseEntity<?> hasUserVotedInSession(
            @RequestParam String userId,
            @PathVariable Long sessionId) {
        boolean hasVoted = votingService.hasUserVotedInSession(userId, sessionId);
        return ResponseEntity.ok(hasVoted);
    }
  
}