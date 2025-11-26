package com.example.hiring_process_flow.controllers;

import com.example.hiring_process_flow.dto.ApplicationStartProcessResponse;
import com.example.hiring_process_flow.entities.Candidate;
import com.example.hiring_process_flow.entities.CandidateStatus;
import com.example.hiring_process_flow.services.ApplicationService;
import lombok.AllArgsConstructor;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/applications")
@AllArgsConstructor
public class ApplicationController {

    private final RuntimeService runtimeService;
    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<ApplicationStartProcessResponse> createCandidate(@RequestBody Candidate candidate) {
        Candidate candidateRecord = applicationService.createCandidate(candidate);

        Map<String, Object> vars = new HashMap<>();
        vars.put("candidateId", candidateRecord.getId());
        vars.put("candidateName", candidateRecord.getFirstName());
        vars.put("candidateSurname", candidateRecord.getLastName());
        vars.put("candidateEmail", candidateRecord.getEmail());
        vars.put("cvText", candidateRecord.getCv());

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("hiringProcess", vars);

        ApplicationStartProcessResponse applicationStartProcessResponse = new ApplicationStartProcessResponse();
        applicationStartProcessResponse.setProcessInstanceId(processInstance.getId());
        applicationStartProcessResponse.setCandidateId(candidateRecord.getId());
        applicationStartProcessResponse.setStatus(CandidateStatus.APPLIED.toString());

        return ResponseEntity.ok(applicationStartProcessResponse);
    }

    @GetMapping("/{candidateId}")
    public ResponseEntity<Candidate> getCandidate(@PathVariable Long candidateId) {
        Optional<Candidate> candidate = applicationService.getCandidate(candidateId);
        return candidate.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
