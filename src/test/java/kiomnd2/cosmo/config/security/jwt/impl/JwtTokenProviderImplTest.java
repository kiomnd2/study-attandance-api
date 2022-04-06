package kiomnd2.cosmo.config.security.jwt.impl;

import kiomnd2.cosmo.config.security.jwt.JwtTokenProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
class JwtTokenProviderImplTest {

    @Autowired
    private JwtTokenProvider<String> jwtTokenProvider;

    @Test
    void createTokenTest() throws Exception {
        final String subject = "test11";
        String token = jwtTokenProvider.createToken(subject);

        assertThat(jwtTokenProvider.getTokenInfo(token).getSubject()).isEqualTo(subject);
    }

}