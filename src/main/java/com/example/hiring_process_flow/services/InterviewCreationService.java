package com.example.hiring_process_flow.services;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

// This service task is used to create interview if the candidate approves the invitation
@Service("createInterviewDelegate")
public class InterviewCreationService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("Interview created!");
    }
}
