package com.midnight.springbatchquartz.controller;

import com.midnight.springbatchquartz.service.BatchJobService;
import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/batch")
public class BatchJobController {

    private final BatchJobService jobService;

    private final Job manualJob;

    public BatchJobController(BatchJobService jobService, @Qualifier("manualBatchJob") Job manualJob) {
        this.jobService = jobService;
        this.manualJob = manualJob;
    }

    @GetMapping(path = "/manual")
    public String startManualBatchJob(){

        return jobService.triggerBatchJob(manualJob);
    }
}
