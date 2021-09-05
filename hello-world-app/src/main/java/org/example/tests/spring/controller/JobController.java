package org.example.tests.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.tests.spring.model.JobRequest;
import org.example.tests.spring.model.JobResult;
import org.example.tests.spring.service.JobLaunchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.*;

@Slf4j
@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobLaunchService jobLaunchService;

    @Autowired
    public JobController(JobLaunchService jobLaunchService){
        this.jobLaunchService = jobLaunchService;
    }

    @PostMapping(consumes = {MULTIPART_FORM_DATA_VALUE, MULTIPART_MIXED_VALUE, APPLICATION_JSON_VALUE},
    produces = APPLICATION_JSON_VALUE)
    public JobResult runJob(@RequestBody JobRequest jobRequest){
        log.info("Received {}", jobRequest);
        return jobLaunchService.runJob(jobRequest);
    }
}
