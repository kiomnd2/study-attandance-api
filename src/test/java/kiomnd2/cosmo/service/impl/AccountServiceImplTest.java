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
        String email = "kiomnd2@naver.com";
        AccountApi.JoinRequest request = AccountApi.JoinRequest.builder()
                .id(123L)
                .nickname(nickname).email(email).build();

        AccountDto accountDto = accountService.createAccount(request);
        Long id = accountDto.getId();

        Account byId = accountRepository.getById(id);

    }

    @Transactional
    @Test
    void checkEmailToken() throws Exception {
        String nickname = "test";
        String email = "kiomnd2@naver.com";
        AccountApi.JoinRequest request = AccountApi.JoinRequest.builder()
                .id(123L)
                .nickname(nickname).email(email).build();

        AccountDto accountDto = accountService.createAccount(request);

        final String emailCheckToken = accountDto.getEmailCheckToken();

        AccountDto accountDto1 = accountService.checkEmailToken(emailCheckToken, accountDto.getId());
        Assertions.assertThat(accountDto1.getEmailCheckToken()).isEqualTo(accountDto.getEmailCheckToken());
        Assertions.assertThat(accountDto1.isEmailVerified()).isTrue();
        Assertions.assertThat(accountDto1.getJoinAt()).isNotNull();

    }
}