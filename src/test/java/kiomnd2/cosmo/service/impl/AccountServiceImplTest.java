package kiomnd2.cosmo.service.impl;

import kiomnd2.cosmo.api.AccountApi;
import kiomnd2.cosmo.domain.entity.Account;
import kiomnd2.cosmo.dto.AccountDto;
import kiomnd2.cosmo.repository.AccountRepository;
import kiomnd2.cosmo.service.AccountService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
class AccountServiceImplTest {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Transactional
    @Test
    void createAccountTest() throws Exception {
        String nickname = "test";
        String password = "test11";
        String email = "kiomnd2@naver.com";
        AccountApi.JoinRequest request = AccountApi.JoinRequest.builder()
                .nickname(nickname).email(email).build();

        AccountDto accountDto = accountService.processNewAccount(request);
        Long id = accountDto.getId();

        Account byId = accountRepository.getById(id);

        Assertions.assertThat(byId.getNickname()).isEqualTo(nickname);
        Assertions.assertThat(byId.getEmail()).isEqualTo(email);
        Assertions.assertThat(byId.getPassword()).isEqualTo(password);
        Assertions.assertThat(byId.getEmailCheckToken()).isNotBlank();
    }

    @Transactional
    @Test
    void checkEmailToken() throws Exception {
        String nickname = "test";
        String email = "kiomnd2@naver.com";
        AccountApi.JoinRequest request = AccountApi.JoinRequest.builder()
                .nickname(nickname).email(email).build();

        AccountDto accountDto = accountService.processNewAccount(request);

        final String emailCheckToken = accountDto.getEmailCheckToken();

        AccountDto accountDto1 = accountService.checkEmailToken(emailCheckToken, email);
        Assertions.assertThat(accountDto1.getEmailCheckToken()).isEqualTo(accountDto.getEmailCheckToken());
        Assertions.assertThat(accountDto1.isEmailVerified()).isTrue();
        Assertions.assertThat(accountDto1.getJoinAt()).isNotNull();

    }
}