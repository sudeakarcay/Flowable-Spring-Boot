package com.example.hiring_process_flow.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ApplicationStartProcessResponse {
    private String processInstanceId;
    private Long candidateId;
    private String status;
}
