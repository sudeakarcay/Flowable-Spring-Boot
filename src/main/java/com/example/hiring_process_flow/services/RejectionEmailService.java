package com.example.hiring_process_flow.services;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

// This service is used to send rejection email if HR does not approve the candidate.
@Service("sendRejectionEmailService")
public class RejectionEmailService implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("Rejection email sent!");
    }
}
