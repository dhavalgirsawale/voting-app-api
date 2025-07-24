package com.example.votingapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "votes")
@Data
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "option_name")
    private VoteOption voteOption;
    
    @ManyToOne
    @JoinColumn(name = "session_id")
    private VotingSession session;
}