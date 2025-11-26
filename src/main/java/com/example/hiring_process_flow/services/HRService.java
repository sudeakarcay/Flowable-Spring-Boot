package com.example.hiring_process_flow.services;

import com.example.hiring_process_flow.dto.HRDecisionResponse;
import com.example.hiring_process_flow.dto.HRTaskResponse;
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
public class HRService {
    private final TaskService taskService;
    private final RuntimeService runtimeService;
    private final ApplicationService applicationService;

    public List<HRTaskResponse> getHRTasks() {
        List<Task> tasks = taskService.createTaskQuery()
                .taskDefinitionKey("hrApprovalTask")
                .active()
                .list();

        return tasks.stream()
                .map(task -> {
                    Map<String, Object> vars = runtimeService.getVariables(task.getProcessInstanceId());

                    HRTaskResponse hrTaskResponse = new HRTaskResponse();
                    hrTaskResponse.setTaskId(task.getId());
                    hrTaskResponse.setProcessInstanceId(task.getProcessInstanceId());
                    hrTaskResponse.setCandidateId((Long) vars.get("candidateId"));
                    hrTaskResponse.setCandidateName((String) vars.get("candidateName"));
                    hrTaskResponse.setCandidateSurname((String) vars.get("candidateSurname"));
                    hrTaskResponse.setCategory((String) vars.get("category"));
                    hrTaskResponse.setCv((String) vars.get("cvText"));
                    System.out.println(hrTaskResponse.getTaskId());
                    return hrTaskResponse;
                })
                .collect(Collectors.toList());
    }

    public HRDecisionResponse makeHRDecision(String taskId, Boolean decision) {
        System.out.println(taskId);
        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .singleResult();

        if (task == null) {
            throw new IllegalArgumentException("No task found with id: " + taskId);
        }

        Long candidateID = (Long) runtimeService.getVariable(task.getProcessInstanceId(), "candidateId");
        runtimeService.setVariable(task.getProcessInstanceId(), "hrApproved", decision);

        taskService.complete(task.getId());

        if (decision) {
            applicationService.updateCandidateStatus(candidateID, CandidateStatus.APPROVED);
        } else  {
            applicationService.updateCandidateStatus(candidateID, CandidateStatus.REJECTED);
        }

        HRDecisionResponse hrDecisionResponse = new HRDecisionResponse();
        hrDecisionResponse.setCandidateId(candidateID);
        hrDecisionResponse.setProcessInstanceId(task.getProcessInstanceId());
        String hrDecision = decision ? CandidateStatus.APPROVED.toString() : CandidateStatus.REJECTED.toString();
        hrDecisionResponse.setDecision(hrDecision);

        return hrDecisionResponse;
    }
}
