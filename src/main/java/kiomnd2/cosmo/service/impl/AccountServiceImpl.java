package kiomnd2.cosmo.service.impl;

import kiomnd2.cosmo.api.AccountApi;
import kiomnd2.cosmo.domain.entity.Account;
import kiomnd2.cosmo.dto.AccountDto;
import kiomnd2.cosmo.repository.AccountRepository;
import kiomnd2.cosmo.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final JavaMailSender javaMailSender;

    @Transactional(readOnly = false)
    @Override
    public AccountDto processNewAccount(AccountApi.Request request) {
        Account account = Account.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(request.getPassword()) // TODO 암호화
                .alarmStudyUpdatedByEmail(request.isAlarmStudyCreated())
                .alarmStudyEnrollmentResultByEmail(request.isAlarmStudyEnrollmentResult())
                .alarmStudyEnrollmentResultByEmail(request.isAlarmStudyUpdatedByEmail())
                .build();

        Account newAccount = accountRepository.save(account);

        sendCheckMail(newAccount);

        return newAccount.toDto();
    }

    private void sendCheckMail(Account newAccount) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        newAccount.generateEmailCheckToken();
        mailMessage.setTo(newAccount.getEmail());
        mailMessage.setSubject("코스모, 회원 가입 인증");
        mailMessage.setText("/check-email-token?token=" + newAccount.getEmailCheckToken() +
                "&email=" + newAccount.getEmail());
        javaMailSender.send(mailMessage);
    }
}
