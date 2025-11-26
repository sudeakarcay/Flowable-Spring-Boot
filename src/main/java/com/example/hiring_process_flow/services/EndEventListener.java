package com.example.hiring_process_flow.services;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Service;

// This execution listener is used to track end events. I need to log if they end or not.
@Service("endEventListener")
@Slf4j
public class EndEventListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution) {
        String processInstanceId = execution.getProcessInstanceId();
        log.info("Candidate task ended for processInstanceId {}", processInstanceId);
    }
}
