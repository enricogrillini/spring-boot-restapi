package it.eg.cookbook.service;

import it.eg.cookbook.error.ApiException;
import it.eg.cookbook.model.Job;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AsyncService {

    @Async
    public void runJob(Job job) {
        log.info("IN  | Execute method asynchronously. Thread: {}", Thread.currentThread().getName());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new ApiException(e);
        }
        log.info("OUT  | Execute method asynchronously. Thread: {}", Thread.currentThread().getName());
    }

}
