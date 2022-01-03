package kiomnd2.cosmo.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class AccountApiTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("회원 가입 API")
    @Test
    void joinAccountApi() throws Exception {
        final String nickName = "홍길동";
        final String password = "qwer1234!@";
        final String email = "test@email.com";

        AccountApi.Request account = AccountApi.Request.builder()
                .nickname(nickName)
                .password(password)
                .email(email)
                .build();
        mockMvc.perform(post("/api/v1/join")
                        .content(objectMapper.writeValueAsBytes(account))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("회원 가입 API - 입력값 오류")
    @Test
    void joinAccount_with_wrong_input() throws Exception{
        final String nickName = "홍길동";
        final String password = "qwer1234!@";
        final String email = "testemail.com"; // 잘못된 이메일

        AccountApi.Request account = AccountApi.Request.builder()
                .nickname(nickName)
                .password(password)
                .email(email)
                .build();
        mockMvc.perform(post("/api/v1/join")
                        .content(objectMapper.writeValueAsBytes(account))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }
}
