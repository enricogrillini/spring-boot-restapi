package it.eg.cookbook.service;

import it.eg.cookbook.error.ApiException;
import it.eg.cookbook.model.Job;
import it.eg.cookbook.model.JobStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class AsyncService implements InitializingBean {

    @Value("${cookbook.async.sleep}")
    long sleep;

    Map<String, Job> map;

    @Override
    public void afterPropertiesSet() throws Exception {
        map = new HashMap<>();
    }

    public Job createJob() {
        Job job = new Job()
                .id(UUID.randomUUID().toString())
                .description("Prova Job")
                .status(JobStatus.RUNNING);

        map.put(job.getId(), job);

        return job;
    }

    @Async
    public void runJob(String id) {
        log.info("IN  | Execute method asynchronously. Thread: {}", Thread.currentThread().getName());

        try {
            Job job = getJob(id);

            Thread.sleep(sleep);
            job.setStatus(JobStatus.COMPLETED);

        } catch (InterruptedException e) {
            throw new ApiException(e);
        }
        log.info("OUT  | Execute method asynchronously. Thread: {}", Thread.currentThread().getName());
    }

    public Job getJob(String id) {
        return map.get(id);
    }

}
