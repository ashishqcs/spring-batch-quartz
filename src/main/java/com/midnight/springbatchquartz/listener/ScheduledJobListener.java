package com.midnight.springbatchquartz.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ScheduledJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("--> inside beforeJob for {}: {}", jobExecution.getJobInstance().getJobName(), jobExecution.getJobId());
        jobExecution.getExecutionContext().put("beforeJobParam", "This is execution context param");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("<-- inside afterJob for {} : {}", jobExecution.getJobInstance().getJobName(), jobExecution.getJobId());
    }
}
