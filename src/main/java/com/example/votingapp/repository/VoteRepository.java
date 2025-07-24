package com.example.votingapp.repository;

import com.example.votingapp.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    // existsByUserUserIdAndSessionId
    boolean existsByUserUserIdAndSessionId(String userId, Long sessionId);
}