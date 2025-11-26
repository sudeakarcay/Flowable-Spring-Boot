package com.example.hiring_process_flow.repositories;

import com.example.hiring_process_flow.entities.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository <Candidate, Long> {
}
