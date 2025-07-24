package com.example.votingapp.repository;

import com.example.votingapp.model.VoteOption;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VoteOptionRepository extends JpaRepository<VoteOption, String> {
    List<VoteOption> findBySessionId(Long sessionId);
    List<VoteOption> findBySessionIdAndOptionName(Long sessionId, String optionName);
}