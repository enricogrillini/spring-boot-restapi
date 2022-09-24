package it.eg.cookbook;

import it.eg.cookbook.model.User;
import it.eg.cookbook.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityTest {

    @Autowired
    JwtService jwtService;

    @Test
    void postGenerateTokenTest() {
        String token = jwtService.createJWT(User.builder()
                .issuer("www.idm.com")
                .subject("admin")
                .audience("progetto-cookbook")
                .customClaim("customClaim")
                .ttlMillis(Long.valueOf(3600 * 1000))
                .build());

        assertNotNull(jwtService.parseToken(token));
    }
}
