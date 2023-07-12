package it.eg.cookbook;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.eg.cookbook.model.Job;
import it.eg.cookbook.model.JobStatus;
import it.eg.cookbook.service.AsyncService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import org.awaitility.core.ThrowingRunnable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class JobControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AsyncService asyncService;

    private static final String URI = "/api/v1/job";

    @Test
    void submitJobTest() throws Exception {
        // Sottometto il Job
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .post(URI)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        Job job = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Job.class);

        // Verifico che sia completato entro 5 secondi
        Awaitility.await()
                .with()
                .pollDelay(500, TimeUnit.MILLISECONDS)
                .pollInterval(500, TimeUnit.MILLISECONDS)
                .atMost(5000, TimeUnit.MILLISECONDS)
                .untilAsserted(new ThrowingRunnable() {
                    @Override
                    public void run() throws Throwable {
                        Assertions.assertEquals(JobStatus.COMPLETED, asyncService.getJob(job.getId()).getStatus());
                    }
                });
    }


    @Test
    void getJobTest() throws Exception {
        // Sottometto il Job
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .post(URI)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        Job job = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Job.class);

        // Leggo lo stato del Job
        mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .get(URI + "/" + job.getId())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        Job job2 = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Job.class);

        Assertions.assertEquals(job.getId(), job2.getId());
        Assertions.assertEquals(job.getDescription(), job2.getDescription());
        Assertions.assertEquals(JobStatus.RUNNING, job2.getStatus());

    }

}