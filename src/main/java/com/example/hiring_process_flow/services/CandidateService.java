package com.example.hiring_process_flow.services;

import com.example.hiring_process_flow.dto.CandidateDecisionResponse;
import com.example.hiring_process_flow.dto.CandidateTaskResponse;
import com.example.hiring_process_flow.entities.CandidateStatus;
import lombok.RequiredArgsConstructor;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidateService {
    private final TaskService taskService;
    private final RuntimeService runtimeService;
    private final ApplicationService applicationService;

    public List<CandidateTaskResponse> getCandidateTasks() {
        List<Task> tasks = taskService.createTaskQuery()
                .taskDefinitionKey("candidateTask")
                .active()
                .list();

        return tasks.stream()
                .map(task -> {
                    Map<String, Object> vars = runtimeService.getVariables(task.getProcessInstanceId());

                    CandidateTaskResponse candidateTaskResponse = new CandidateTaskResponse();
                    candidateTaskResponse.setTaskId(task.getId());
                    candidateTaskResponse.setProcessInstanceId(task.getProcessInstanceId());
                    candidateTaskResponse.setCandidateId((Long) vars.get("candidateId"));
                    candidateTaskResponse.setFirstName((String) vars.get("candidateName"));
                    candidateTaskResponse.setLastName((String) vars.get("candidateSurname"));
                    candidateTaskResponse.setCategory((String) vars.get("category"));
                    System.out.println(candidateTaskResponse.getTaskId());
                    return candidateTaskResponse;
                })
                .collect(Collectors.toList());
    }

    public CandidateDecisionResponse makeCandidateDecision(String taskId, Boolean decision) {
        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .singleResult();

        if (task == null) {
            throw new IllegalArgumentException("No task found with id: " + taskId);
        }

        Long candidateID = (Long) runtimeService.getVariable(task.getProcessInstanceId(), "candidateId");
        runtimeService.setVariable(task.getProcessInstanceId(), "willAttend", decision);

        taskService.complete(task.getId());

        if (decision) {
            applicationService.updateCandidateStatus(candidateID, CandidateStatus.ATTENDING);
        } else {
            applicationService.updateCandidateStatus(candidateID, CandidateStatus.NOT_ATTENDING);
        }

        CandidateDecisionResponse candidateDecisionResponse = new CandidateDecisionResponse();
        candidateDecisionResponse.setCandidateId(candidateID);
        candidateDecisionResponse.setProcessInstanceId(task.getProcessInstanceId());

        if (decision) {
            candidateDecisionResponse.setCandidateDecision(CandidateStatus.ATTENDING.toString());
        } else {
            candidateDecisionResponse.setCandidateDecision(CandidateStatus.NOT_ATTENDING.toString());
        }

        return candidateDecisionResponse;
    }
}