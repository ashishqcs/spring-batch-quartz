package com.midnight.springbatchquartz.service;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

import static java.lang.System.currentTimeMillis;

@Service
@AllArgsConstructor
public class BatchJobService {

    private final JobLauncher jobLauncher;

    public String triggerBatchJob(Job job) {

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startTime", currentTimeMillis())
                .toJobParameters();

        try {
            return jobLauncher.run(job, jobParameters).getStatus().name();
        } catch (JobExecutionException e) {
            e.printStackTrace();
        }

        return BatchStatus.FAILED.name();
    }
}
