package kiomnd2.cosmo.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kiomnd2.cosmo.config.security.jwt.JwtTokenProvider;
import kiomnd2.cosmo.config.security.jwt.Token;
import kiomnd2.cosmo.dto.AccountDto;
import kiomnd2.cosmo.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class AccountApiTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JwtTokenProvider<Long> tokenProvider;

    @Autowired
    AccountService accountService;

    @DisplayName("회원 가입 API")
    @Test
    void joinAccountApi() throws Exception {
        final String nickName = "홍길동";
        final String email = "test@email.com";

        AccountApi.JoinRequest account = AccountApi.JoinRequest.builder()
                .nickname(nickName)
                .email(email)
                .build();
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/logged-in")
                        .content(objectMapper.writeValueAsBytes(account))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Response<AccountApi.JoinResponse> response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<Response<AccountApi.JoinResponse>>() {});
        Token tokenInfo = tokenProvider.getTokenInfo(response.getBody().getToken());
        assertThat(response.getBody().getAccount().getId().toString()).isEqualTo(tokenInfo.getSubject());
        assertThat(response.getBody().getAccount().getEmail()).isEqualTo(email);
        assertThat(response.getBody().getAccount().getNickname()).isEqualTo(nickName);
    }

    @DisplayName("회원 가입 API - 입력값 오류")
    @Test
    void joinAccount_with_wrong_input() throws Exception{
        final String nickName = "홍길동";
        final String email = "testemail.com"; // 잘못된 이메일

        AccountApi.JoinRequest account = AccountApi.JoinRequest.builder()
                .nickname(nickName)
                .email(email)
                .build();
        mockMvc.perform(post("/api/v1/logged-in")
                        .content(objectMapper.writeValueAsBytes(account))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @DisplayName("회원 가입 과 이메일 체크 API TEST")
    @Test
    void joinAccountAndCheckEmail() throws Exception {
        final String nickName = "홍길동";
        final String email = "test@email.com";

        AccountApi.JoinRequest request = AccountApi.JoinRequest.builder()
                .nickname(nickName).email(email).build();

        AccountDto accountDto = accountService.getAccount(request);

        final String token = accountDto.getEmailCheckToken();
        final String cEmail = accountDto.getEmail();

        mockMvc.perform(get("/check-email-token")
                .param("token", token)
                .param("email", cEmail))
                .andExpect(status().isOk())
                .andExpect(model().attributeDoesNotExist("error"))
                .andExpect(model().attributeExists("nickname"))
                .andExpect(view().name("account/checked-Email"));

    }
}
