package com.example.hiring_process_flow.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class CandidateQuartzScheduler {
    private final Scheduler scheduler;

    public void schedule(String taskId, String processInstanceId, Long candidateId) {
        try {
            JobDetail jobDetail = JobBuilder.newJob(CandidateAutoRejectJob.class)
                    .withIdentity("candidateQuartzJob-" + taskId) // creating id for quartz job
                    .usingJobData("taskId", taskId)
                    .usingJobData("processInstanceId", processInstanceId)
                    .usingJobData("candidateId", candidateId)
                    .build();

            // Schedule period should be an hour.
            Date startAt = Date.from(Instant.now().plus(1, ChronoUnit.MINUTES));

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("candidateJobTrigger-" + taskId)
                    .startAt(startAt)
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule())
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
            log.info("Scheduling Candidate Quartz Job: {}", jobDetail.getKey());
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
