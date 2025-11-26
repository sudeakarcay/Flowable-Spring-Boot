package com.example.hiring_process_flow.services;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("candidateTaskListener")
public class CandidateTaskListener implements TaskListener {
    @Autowired
    private CandidateQuartzScheduler candidateQuartzScheduler;

    @Override
    public void notify(DelegateTask delegateTask) {
        String taskId = delegateTask.getId();
        String processInstanceId = delegateTask.getProcessInstanceId();
        Long candidateId = (Long) delegateTask.getVariable("candidateId");
        candidateQuartzScheduler.schedule(taskId, processInstanceId, candidateId);
    }
}
