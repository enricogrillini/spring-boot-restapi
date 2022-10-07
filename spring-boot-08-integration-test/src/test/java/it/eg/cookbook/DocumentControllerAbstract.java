package it.eg.cookbook;

import it.eg.cookbook.error.ResponseCode;
import it.eg.cookbook.model.DocumentPojo;
import it.eg.cookbook.model.ResponseMessage;
import it.eg.cookbook.model.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.test.annotation.Commit;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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

    @Test
    @Order(1)
    void getDocuments() {
        doRestTest(URI, HttpMethod.GET, "", null, HttpStatus.OK);
    }


    @Test
    @Order(2)
    void getDocument() {
        doRestTest(URI_ID, HttpMethod.GET, "", null, HttpStatus.OK, 1);
    }

    @Test
    @Order(3)
    void getDocumentKO() {
        doRestTest(URI_ID, HttpMethod.GET, "", null, HttpStatus.NOT_FOUND, 100);
    }

    @Test
    @Order(4)
    void deleteDocument() {
        assertEquals(1, jdbcTemplate.queryForObject("Select count(*) from document where Id = 1", Integer.class));

        doRestTest(URI_ID, HttpMethod.DELETE, "", null, HttpStatus.OK, 1);

        assertEquals(0, jdbcTemplate.queryForObject("Select count(*) from document where Id = 1", Integer.class));
    }

    @Test
    @Order(5)
    void deleteDocumentKO() throws Exception {
        assertEquals(0, jdbcTemplate.queryForObject("Select count(*) from document where Id = 100", Integer.class));

        doRestTest(URI_ID, HttpMethod.DELETE, "", null, HttpStatus.NOT_FOUND, 100);
    }


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


    @Test
    @Order(7)
    void postDocument() throws Exception {
        assertEquals(0, jdbcTemplate.queryForObject("Select count(*) from document where name = 'doc-5'", Integer.class));

        doRestTest(URI, HttpMethod.POST, "", readRequestFile(), HttpStatus.OK);

        DocumentPojo documentPojo = jdbcTemplate.queryForObject("Select * from document where name = 'doc-5'", new BeanPropertyRowMapper<>(DocumentPojo.class));
        assertJsonEquals(readExpectedFile("-pojo"), objectMapper.writeValueAsString(documentPojo));

//        System.out.println(readExpectedFile());
//        DocumentPojo documentPojo = jdbcTemplate.queryForObject("Select * from document where name = 'doc-5'", new BeanPropertyRowMapper<>(DocumentPojo.class));
//        System.out.println(documentPojo);
//        documentPojo = jdbcTemplate.queryForObject("Select * from document where Id = 1", new BeanPropertyRowMapper<>(DocumentPojo.class));

    }

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
}


