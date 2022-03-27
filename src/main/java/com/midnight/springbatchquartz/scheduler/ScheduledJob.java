package com.midnight.springbatchquartz.scheduler;

import com.midnight.springbatchquartz.service.BatchJobService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Slf4j
public class ScheduledJob extends QuartzJobBean {

    @Autowired
    @Qualifier("scheduledBatchJob")
    private Job scheduledBatchJob;

    @Autowired
    private BatchJobService jobService;

    @Override
    protected void executeInternal(JobExecutionContext context) {

        String status = jobService.triggerBatchJob(scheduledBatchJob);
        log.info("Scheduled job finished with status: {}", status);
    }
}
