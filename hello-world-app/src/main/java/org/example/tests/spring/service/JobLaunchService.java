package org.example.tests.spring.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.tests.spring.model.JobRequest;
import org.example.tests.spring.model.JobResult;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class JobLaunchService {

    private final BatchConfigurer batchConfigurer;

    private final JobRegistry jobRegistry;

    @Autowired
    public JobLaunchService(BatchConfigurer batchConfigurer, JobRegistry jobRegistry){
        this.batchConfigurer = batchConfigurer;
        this.jobRegistry = jobRegistry;
    }

    public JobResult runJob(JobRequest jobRequest){
        String jobName = jobRequest.getJobName();
        log.info("Received new job request {} with params {}", jobName, jobRequest.getParams());
        JobExecution jobExecution = null;
        try{
            Job job = loadJob(jobName);
            JobLauncher jobLauncher = batchConfigurer.getJobLauncher();
            JobParameters jobParameters = initJobParameters(jobRequest.getParams());
            jobExecution = jobLauncher.run(job, jobParameters);
        }catch (NoSuchJobException e){
            log.warn(e.getMessage());
            return JobResult.builder().status(HttpStatus.BAD_REQUEST).message(e.getMessage()).build();
        }catch (Exception e){
            log.error(String.format("Request to run job %s failed", jobName), e);
            return JobResult.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(e.getMessage()).build();
        }
        return JobResult.builder().status(HttpStatus.OK).message(jobExecution.getExitStatus().getExitDescription()).build();
    }

    private JobParameters initJobParameters(Map<String, String> params) {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        params.forEach(jobParametersBuilder::addString);
        return jobParametersBuilder.toJobParameters();
    }

    @SneakyThrows
    private Job loadJob(String jobName) {
        log.info("Jobs registered : {}", jobRegistry.getJobNames());
        return jobRegistry.getJob(jobName);
    }

}
