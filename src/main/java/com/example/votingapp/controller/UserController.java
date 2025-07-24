package com.example.votingapp.controller;

import com.example.votingapp.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private VotingService votingService;
    
    @GetMapping("/sessions")
    public ResponseEntity<?> getActiveSessions() {
        return ResponseEntity.ok(votingService.getActiveSessions());
    }
    
    @GetMapping("/options")
    public ResponseEntity<?> getOptions(@RequestParam Long sessionId) {
        return ResponseEntity.ok(votingService.getOptions(sessionId));
    }
    
    @PostMapping("/vote")
    public ResponseEntity<?> castVote(
            @RequestParam String userId,
            @RequestParam Long sessionId,
            @RequestParam String option) {
        try {
            votingService.castVote(userId, sessionId, option);
            return ResponseEntity.ok("Vote cast successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}