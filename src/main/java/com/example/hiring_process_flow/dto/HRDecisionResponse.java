package com.example.hiring_process_flow.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class HRDecisionResponse {
    private String processInstanceId;
    private Long candidateId;
    private String decision;
}

