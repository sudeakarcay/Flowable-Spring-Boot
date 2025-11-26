package com.example.hiring_process_flow.services;

import com.example.hiring_process_flow.entities.Candidate;
import com.example.hiring_process_flow.entities.CandidateStatus;
import com.example.hiring_process_flow.repositories.CandidateRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Transactional
    public Candidate createCandidate(Candidate candidate) {
        candidate.setStatus(CandidateStatus.APPLIED);
        return candidateRepository.save(candidate);
    }

    public Optional<Candidate> getCandidate(Long candidateId) {
        return candidateRepository.findById(candidateId);
    }

    @Transactional
    public void updateCandidateStatus(Long candidateId, CandidateStatus status) {
        Candidate candidate = candidateRepository.findById(candidateId).orElse(null);
        candidate.setStatus(status);
        candidateRepository.save(candidate);
    }
}
