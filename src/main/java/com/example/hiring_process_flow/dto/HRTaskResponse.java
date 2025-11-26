package com.example.hiring_process_flow.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class HRTaskResponse {
    private String taskId;
    private String processInstanceId;
    private Long candidateId;
    private String candidateName;
    private String candidateSurname;
    private String category;
    private String cv;
}
