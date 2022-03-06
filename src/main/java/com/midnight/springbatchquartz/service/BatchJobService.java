package com.midnight.springbatchquartz.service;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

import static java.lang.System.currentTimeMillis;

@Service
public class BatchJobService {

    private final JobLauncher jobLauncher;

    private final Job manualJob;

    public BatchJobService(JobLauncher jobLauncher, Job manualJob) {
        this.jobLauncher = jobLauncher;
        this.manualJob = manualJob;
    }

    public String triggerManualJob() {

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", currentTimeMillis())
                .toJobParameters();

        try {
            return jobLauncher.run(manualJob, jobParameters).getStatus().name();
        } catch (JobExecutionException e) {
            e.printStackTrace();
        }

        return BatchStatus.FAILED.name();
    }
}
