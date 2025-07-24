package com.example.votingapp.repository;

import com.example.votingapp.model.VotingSession;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VotingSessionRepository extends JpaRepository<VotingSession, Long> {
    List<VotingSession> findByIsActiveTrue();
}