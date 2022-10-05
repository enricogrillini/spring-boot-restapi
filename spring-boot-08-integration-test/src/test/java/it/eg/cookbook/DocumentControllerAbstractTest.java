package it.eg.cookbook;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class DocumentControllerAbstractTest extends AbstractTest {




    @Test
    @Order(1)
    void getDocumentsTest() throws Exception {
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
    }
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

}