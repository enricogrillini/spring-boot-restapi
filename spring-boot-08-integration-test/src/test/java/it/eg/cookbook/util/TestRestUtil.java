package it.eg.cookbook.util;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.TestInfo;
import org.springframework.http.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class TestRestUtil extends TestAbstractUtil {

    @Getter
    private RestTemplate restTemplate;

    public TestRestUtil(TestInfo testInfo) {
        super(testInfo);
        restTemplate = new RestTemplate();
    }


//    public static HttpHeaders noLoggedHeader() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
//
//        return headers;
//    }
//
//    public static HttpHeaders loggedHeader() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
//        headers.add(HttpHeaders.AUTHORIZATION, "prova");
//
//        return headers;
//    }

    protected void testGet(String apiUrl, HttpStatus expectedHttpStatus, HttpHeaders httpHeaders, Object... uriVariables) {
        HttpStatus httpStatus;
        MediaType mediaType;
        String body;

        try {
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class, uriVariables);
            httpStatus = response.getStatusCode();
            mediaType = response.getHeaders().getContentType();
            body = response.getBody();
        } catch (HttpStatusCodeException e) {
            httpStatus = e.getStatusCode();
            mediaType = e.getResponseHeaders().getContentType();
            body = e.getResponseBodyAsString();
        }

        assertEquals(expectedHttpStatus, httpStatus);
        assertEquals(MediaType.APPLICATION_JSON, mediaType);
        assertJsonEquals(readExpectedFile(), body);
    }


}
