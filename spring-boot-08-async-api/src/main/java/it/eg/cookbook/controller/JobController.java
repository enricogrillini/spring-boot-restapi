package it.eg.cookbook.controller;

import it.eg.cookbook.model.Job;
import it.eg.cookbook.model.JobStatus;
import it.eg.cookbook.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class JobController implements JobApi {

    @Autowired
    AsyncService asyncService;

    @Override
    public ResponseEntity<Job> getJob() {
        return null;
    }

    @Override
    public ResponseEntity<Job> submitJob() {
        Job job = new Job()
                .id(UUID.randomUUID().toString())
                .description("Prova Job")
                .status(JobStatus.RUNNING);

        asyncService.runJob(job);
        return ResponseEntity.ok(job);
    }
}

