package it.eg.cookbook.controller;

import it.eg.cookbook.error.ApiException;
import it.eg.cookbook.error.ResponseCode;
import it.eg.cookbook.model.Job;
import it.eg.cookbook.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobController implements JobApi {

    @Autowired
    AsyncService asyncService;

    @Override
    public ResponseEntity<Job> getJob(String id) {
        Job job = asyncService.getJob(id);
        if (job == null) {
            throw new ApiException(ResponseCode.NOT_FOUND, "Jod id non trovato: " + id);
        } else {
            return ResponseEntity.ok(job);
        }
    }

    @Override
    public ResponseEntity<Job> submitJob() {
        Job job = asyncService.createJob();

        asyncService.runJob(job.getId());

        return ResponseEntity.ok(job);
    }
}

