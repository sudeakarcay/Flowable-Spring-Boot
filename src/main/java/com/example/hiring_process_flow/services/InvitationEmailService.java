package com.example.hiring_process_flow.services;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

// This service task is used to send interview invitation after HR approves the candidate.
@Service("sendInterviewInviteService")
public class InvitationEmailService implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("Interview invitation sent! You have an hour to respond!");
        execution.setVariable("hrApproved", true);
    }
}
