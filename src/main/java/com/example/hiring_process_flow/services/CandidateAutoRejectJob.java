package com.example.hiring_process_flow.services;

import com.example.hiring_process_flow.entities.CandidateStatus;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CandidateAutoRejectJob implements Job {
    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ApplicationService applicationService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap context = jobExecutionContext.getMergedJobDataMap();
        String taskId = context.getString("taskId");
        Long candidateId = context.getLong("candidateId");
        String processInstanceId = context.getString("processInstanceId");

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            return;
        }

        Boolean candidateDecision = (Boolean) runtimeService.getVariable(processInstanceId, "willAttend");
        if (candidateDecision != null) {
            return;
        }

        // If there is no response came from the candidate then quartz should cancel the event.
        // willAttend should be set to false.
        // Candidate status should be set to NOT_ATTENDING by Quartz.
        runtimeService.setVariable(processInstanceId, "willAttend", false);
        applicationService.updateCandidateStatus(candidateId, CandidateStatus.NOT_ATTENDING);
        log.info("Candidate Auto Reject Job Completed");
    }
}
