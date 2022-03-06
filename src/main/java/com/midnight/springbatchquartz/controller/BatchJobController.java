package com.midnight.springbatchquartz.controller;

import com.midnight.springbatchquartz.service.BatchJobService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/batch")
public class BatchJobController {

    private final BatchJobService jobService;

    public BatchJobController(BatchJobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping(path = "/manual")
    public String startManualBatchJob(){

        return jobService.triggerManualJob();
    }
}
