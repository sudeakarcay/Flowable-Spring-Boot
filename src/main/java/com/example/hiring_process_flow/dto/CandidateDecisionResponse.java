package com.example.hiring_process_flow.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CandidateDecisionResponse {
    private String processInstanceId;
    private Long candidateId;
    private String candidateDecision;
}
