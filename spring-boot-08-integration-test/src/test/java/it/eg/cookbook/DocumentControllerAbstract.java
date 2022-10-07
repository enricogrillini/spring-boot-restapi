package it.eg.cookbook;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.*;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@Commit
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class DocumentControllerAbstract extends AbstractTest {

    private static final String URI = "http://localhost:8082/api/v1/document";
    private static final String URI_ID = "http://localhost:8082/api/v1/document/{documentId}";

    void testGet(String url, String authorization, HttpStatus expectedStatus, Object... uriVariables) throws RestClientException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.AUTHORIZATION, authorization);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class, uriVariables);
            assertEquals(expectedStatus, response.getStatusCode());
            assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
            assertJsonEquals(readExpectedFile(), response.getBody());
        } catch (HttpStatusCodeException e) {
            assertEquals(expectedStatus, e.getStatusCode());
            assertEquals(MediaType.APPLICATION_JSON, e.getResponseHeaders().getContentType());
            assertJsonEquals(readExpectedFile(), e.getResponseBodyAsString());
        }
    }

    @Test
    @Order(1)
    void getDocuments() {
        testGet(URI, "", HttpStatus.OK);
    }


    @Test
    @Order(2)
    void getDocument() {
        testGet(URI_ID, "", HttpStatus.OK, 1);
    }

    @Test
    @Order(3)
    void getDocument_Ko() {
        testGet(URI_ID, "", HttpStatus.NOT_FOUND, 100);
    }

//    @Test
//    @Order(1)
//    void getDocuments() {
//        DocumentPojo documentPojo = jdbcTemplate.queryForObject("Select * from document where Id = 1", new BeanPropertyRowMapper<>(DocumentPojo.class));
//        System.out.println(documentPojo);
//
//        // Update
//        jdbcTemplate.update("Update document set description = 'pippo' where Id = 1");
//
//        documentPojo = jdbcTemplate.queryForObject("Select * from document where Id = 1", new BeanPropertyRowMapper<>(DocumentPojo.class));
//        System.out.println(readExpectedFile());
//    }
//
//    @Test
//    @Order(2)
//    void getDocumentsTest2() {
//        // Update
//        DocumentPojo documentPojo = jdbcTemplate.queryForObject("Select * from document where Id = 1", new BeanPropertyRowMapper<>(DocumentPojo.class));
//
//        System.out.println(documentPojo);
//    }
}

//        HttpStatus httpStatus;
//        MediaType mediaType;
//        String body;
//
//        try {
//            ResponseEntity<String> response = restTemplate.exchange("http://localhost:8082/api/v1/document", HttpMethod.GET, null, String.class);
//            httpStatus = response.getStatusCode();
//            mediaType = response.getHeaders().getContentType();
//            body = response.getBody();
//        } catch (HttpStatusCodeException e) {
//            httpStatus = e.getStatusCode();
//            mediaType = e.getResponseHeaders().getContentType();
//            body = e.getResponseBodyAsString();
//        }

//        assertEquals(expectedStatus, httpStatus);
//        assertEquals(MediaType.APPLICATION_JSON, mediaType);
//        assertJsonEquals(readExpectedFile(), body);


//        MvcResult mvcResult = mockMvc
//                .perform(MockMvcRequestBuilders
//                        .get(URI)
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                        .header("Authorization", "Bearer " + mockToken("reader-1")))
//                .andReturn();
//
//        // Verifico lo stato della risposta
//        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
//
//        // Verifico che la lista di documenti non sia vuota
//        Document[] documents = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Document[].class);
//        assertEquals(3, documents.length);

//
//    @Test
//    @Order(2)
//    void getDocumentTest() throws Exception {
//        MvcResult mvcResult = mockMvc
//                .perform(MockMvcRequestBuilders
//                        .get(URI_ID, 1)
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                        .header("Authorization", "Bearer " + mockToken("reader-1")))
//                .andReturn();
//
//        // Verifico lo stato della risposta
//        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
//
//        // Verifico che lo Documento sia corretto
//        Document documento = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Document.class);
//        assertEquals(1, documento.getId());
//    }
//
//    @Test
//    @Order(3)
//    void getDocumentTestKO() throws Exception {
//        MvcResult mvcResult = mockMvc
//                .perform(MockMvcRequestBuilders
//                        .get(URI_ID, 100)
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                        .header("Authorization", "Bearer " + mockToken("reader-1")))
//                .andReturn();
//
//        // Verifico lo stato della risposta
//        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
//
//        // Verifico che lo Documento sia corretto
//        ResponseMessage responseMessage = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ResponseMessage.class);
//        assertEquals(ResponseCode.NOT_FOUND.toString(), responseMessage.getCode());
//        assertEquals(ResponseCode.NOT_FOUND.getDescription(), responseMessage.getDescription());
//        assertEquals("Documento non trovato", responseMessage.getDetail());
//    }
//
//    @Test
//    @Order(4)
//    void deleteDocumentTest() throws Exception {
//        MvcResult mvcResult = mockMvc
//                .perform(MockMvcRequestBuilders
//                        .delete(URI_ID, 1)
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                        .header("Authorization", "Bearer " + mockToken("admin-2")))
//                .andReturn();
//
//        // Verifico lo stato della risposta
//        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
//
//        // Verifico che lo Documento sia corretto
//        ResponseMessage responseMessage = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ResponseMessage.class);
//
//        assertEquals(ResponseCode.OK.toString(), responseMessage.getCode());
//        assertEquals(ResponseCode.OK.getDescription(), responseMessage.getDescription());
//        assertEquals("Documento eliminato correttamente", responseMessage.getDetail());
//    }
//
//    @Test
//    @Order(5)
//    void deleteDocumentTestKO() throws Exception {
//        MvcResult mvcResult = mockMvc
//                .perform(MockMvcRequestBuilders
//                        .delete(URI_ID, 100)
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                        .header("Authorization", "Bearer " + mockToken("admin-2")))
//                .andReturn();
//
//        // Verifico lo stato della risposta
//        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
//
//        // Verifico che il Documento sia corretto
//        ResponseMessage responseMessage = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ResponseMessage.class);
//        assertEquals(ResponseCode.NOT_FOUND.toString(), responseMessage.getCode());
//        assertEquals(ResponseCode.NOT_FOUND.getDescription(), responseMessage.getDescription());
//        assertEquals("Documento non trovato", responseMessage.getDetail());
//    }
//
//    @Test
//    @Order(6)
//    void deleteDocumentTestKOSec() throws Exception {
//        MvcResult mvcResult = mockMvc
//                .perform(MockMvcRequestBuilders
//                        .delete(URI_ID, "XX")
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                        .header("Authorization", "Bearer fake"))
//                .andReturn();
//
//        // Verifico lo stato della risposta
//        assertEquals(HttpStatus.FORBIDDEN.value(), mvcResult.getResponse().getStatus());
//    }
//
//    @Test
//    @Order(7)
//    void postDocumentTest() throws Exception {
//        String documentStr = objectMapper.writeValueAsString(mockDocument(5));
//        String jwtToken = jwtService.createJWT(new User().issuer("www.idm.com").subject("writer-2").audience("progetto-cookbook").customClaim("customClaim").ttlMillis(Long.valueOf(3600 * 1000)));
//
//        MvcResult mvcResult = mockMvc
//                .perform(MockMvcRequestBuilders.post(URI)
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(documentStr)
//                        .header("Authorization", "Bearer " + jwtToken))
//                .andReturn();
//
//        // Verifico lo stato della risposta
//        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
//
//        // Verifico che lo Documento sia corretto
//        ResponseMessage responseMessage = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ResponseMessage.class);
//        assertEquals(ResponseCode.OK.toString(), responseMessage.getCode());
//        assertEquals(ResponseCode.OK.getDescription(), responseMessage.getDescription());
//        assertEquals("Documento inserito correttamente", responseMessage.getDetail());
//    }
//
//    @Test
//    @Order(8)
//    void postDocumentTestKO() throws Exception {
//        String documentStr = objectMapper.writeValueAsString(mockDocument(3));
//
//        MvcResult mvcResult = mockMvc
//                .perform(MockMvcRequestBuilders.post(URI)
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(documentStr)
//                        .header("Authorization", "Bearer " + mockToken("writer-2")))
//                .andReturn();
//
//        // Verifico lo stato della risposta
//        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
//
//        // Verifico che lo Documento sia corretto
//        ResponseMessage responseMessage = objectMapper.readValue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8), ResponseMessage.class);
//        assertEquals(ResponseCode.BUSINESS_ERROR.toString(), responseMessage.getCode());
//        assertEquals(ResponseCode.BUSINESS_ERROR.getDescription(), responseMessage.getDescription());
//        assertEquals("Documento già presente", responseMessage.getDetail());
//    }
//
//
//    @Test
//    @Order(9)
//    void postDocumentTestKOSec() throws Exception {
//        String documentStr = objectMapper.writeValueAsString(mockDocument(2));
//
//        MvcResult mvcResult = mockMvc
//                .perform(MockMvcRequestBuilders
//                        .post(URI)
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(documentStr)
//                        .header("Authorization", "Bearer fake"))
//                .andReturn();
//
//        // Verifico lo stato della risposta
//        assertEquals(HttpStatus.FORBIDDEN.value(), mvcResult.getResponse().getStatus());
//    }
//
//    @Test
//    @Order(10)
//    void putDocumentTest() throws Exception {
//        String documentStr = objectMapper.writeValueAsString(mockDocument(2));
//
//        MvcResult mvcResult = mockMvc
//                .perform(MockMvcRequestBuilders
//                        .put(URI)
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(documentStr)
//                        .header("Authorization", "Bearer " + mockToken("writer-2")))
//                .andReturn();
//
//        // Verifico lo stato della risposta
//        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
//
//        // Verifico che lo Documento sia corretto
//        ResponseMessage responseMessage = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ResponseMessage.class);
//        assertEquals(ResponseCode.OK.toString(), responseMessage.getCode());
//        assertEquals(ResponseCode.OK.getDescription(), responseMessage.getDescription());
//        assertEquals("Documento aggiornato correttamente", responseMessage.getDetail());
//    }
//
//    @Test
//    @Order(11)
//    void putDocumentTestKO() throws Exception {
//        String documentStr = objectMapper.writeValueAsString(mockDocument(6));
//
//        MvcResult mvcResult = mockMvc
//                .perform(MockMvcRequestBuilders
//                        .put(URI)
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(documentStr)
//                        .header("Authorization", "Bearer " + mockToken("writer-2")))
//                .andReturn();
//
//        // Verifico lo stato della risposta
//        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
//
//        // Verifico che lo Documento sia corretto
//        ResponseMessage responseMessage = objectMapper.readValue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8), ResponseMessage.class);
//        assertEquals(ResponseCode.NOT_FOUND.toString(), responseMessage.getCode());
//        assertEquals(ResponseCode.NOT_FOUND.getDescription(), responseMessage.getDescription());
//        assertEquals("Documento non trovato", responseMessage.getDetail());
//    }
//
//    @Test
//    @Order(12)
//    void putDocumentTestKOSec() throws Exception {
//        String documentStr = objectMapper.writeValueAsString(mockDocument(6));
//
//        MvcResult mvcResult = mockMvc
//                .perform(MockMvcRequestBuilders
//                        .put(URI)
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(documentStr))
//                .andReturn();
//
//        // Verifico lo stato della risposta
//        assertEquals(HttpStatus.FORBIDDEN.value(), mvcResult.getResponse().getStatus());
//    }

