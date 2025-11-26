package com.example.hiring_process_flow.controllers;

import com.example.hiring_process_flow.dto.CandidateTaskResponse;
import com.example.hiring_process_flow.dto.HRTaskResponse;
import com.example.hiring_process_flow.services.CandidateService;
import com.example.hiring_process_flow.services.HRService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tasks")
public class TasksController {
    private final HRService hrService;
    private final CandidateService candidateService;

    @GetMapping
    public ResponseEntity<?> getTasks(@RequestParam String groupKey) {
        if ("HR".equalsIgnoreCase(groupKey)) {
            List<HRTaskResponse> tasks = hrService.getHRTasks();
            return ResponseEntity.ok(tasks);
        }

        if ("CANDIDATE".equalsIgnoreCase(groupKey)) {
            List<CandidateTaskResponse> tasks = candidateService.getCandidateTasks();
            return ResponseEntity.ok(tasks);
        }

        return ResponseEntity.badRequest().body("Invalid group key " + groupKey);
    }

    @PostMapping("/{taskId}/decision")
    public ResponseEntity<?> makeDecision(@PathVariable String taskId,
                                           @RequestParam String groupKey,
                                           @RequestBody Boolean decision) {
        if ("HR".equalsIgnoreCase(groupKey)) {
            return ResponseEntity.ok(hrService.makeHRDecision(taskId, decision));
        }
        if ("CANDIDATE".equalsIgnoreCase(groupKey)) {
            return ResponseEntity.ok(candidateService.makeCandidateDecision(taskId, decision));
        }

        return ResponseEntity.badRequest().body("Invalid group key " + groupKey);
    }
}
