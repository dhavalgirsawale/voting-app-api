package com.example.votingapp.controller;

import com.example.votingapp.model.User;
import com.example.votingapp.service.AuthService;
import com.example.votingapp.service.UserImportService;
import com.example.votingapp.service.VotingService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    
    @Autowired
    private VotingService votingService;
    
    @Autowired
    private UserImportService userImportService;
    
    @PostMapping("/create-session")
    public ResponseEntity<?> createSession(@RequestParam String title) {
        Long sessionId = votingService.createSession(title);
        return ResponseEntity.ok(sessionId);
    }
    
    @PostMapping("/end-session")
    public ResponseEntity<?> endSession(@RequestParam Long sessionId) {
        votingService.endSession(sessionId);
        return ResponseEntity.ok("Session ended");
    }
    
    @PostMapping("/add-option")
    public ResponseEntity<?> addOption(
            @RequestParam Long sessionId,
            @RequestParam String option) {
        votingService.addOption(sessionId, option);
        return ResponseEntity.ok("Option added");
    }
    
    @GetMapping("/results")
    public ResponseEntity<?> getResults(@RequestParam Long sessionId) {
        return ResponseEntity.ok(votingService.getResults(sessionId));
    }
    
    
    
    @PostMapping("/grant-access")
    public ResponseEntity<?> grantVotingAccess(
            @RequestParam String userId,
            @RequestParam boolean canVote) {
        // Implementation would update user's canVote status
        return ResponseEntity.ok("Voting access updated");
    }

    @PostMapping("/reset-votes")
    public ResponseEntity<?> resetVotes() {
        votingService.resetAllSessions();
        return ResponseEntity.ok("All votes and results have been reset");
    }

    @PostMapping("/hard-reset")
    public ResponseEntity<?> hardReset() {
        votingService.hardResetAllVotingData();
        return ResponseEntity.ok("All voting data has been completely reset except users");
    }
    @PostMapping("/import-users")
    public ResponseEntity<?> importUsers(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Please select a file to upload");
            }
            
            if (!file.getContentType().equals("text/csv")) {
                return ResponseEntity.badRequest().body("Only CSV files are allowed");
            }
            
            List<User> importedUsers = userImportService.importUsersFromCSV(file);
            return ResponseEntity.ok(importedUsers.size() + " users imported successfully");
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to import users: " + e.getMessage());
        }
    }
}