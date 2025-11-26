package com.example.hiring_process_flow.services;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

// This service task is used to categorize candidates after their application.

@Slf4j
@Service("categorizeCvService")
public class CVCategorizationService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        String cv = (String) execution.getVariable("cvText");
        String category = "OTHER";

        if (cv != null) {
            String cvText = cv.toLowerCase();
            if (cvText.contains("backend"))
                category = "BACKEND";
            else if (cvText.contains("frontend"))
                category = "FRONTEND";
            else if (cvText.contains("data")) {
                category = "DATA";
            }
        }
        execution.setVariable("category", category);
        log.info("CV is categorized as: {}", category);
    }
}
