package kiomnd2.cosmo.service.impl;

import kiomnd2.cosmo.api.AccountApi;
import kiomnd2.cosmo.domain.AccountDao;
import kiomnd2.cosmo.dto.AccountDto;
import kiomnd2.cosmo.repository.AccountRepository;
import kiomnd2.cosmo.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.common.reflection.java.JavaMetadataProvider;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final JavaMailSender javaMailSender;

    @Override
    public AccountDto processNewAccount(AccountApi.Request request) {
        AccountDao accountDao = AccountDao.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(request.getPassword()) // TODO 암호화
                .alarmStudyUpdatedByEmail(request.isAlarmStudyCreated())
                .alarmStudyEnrollmentResultByEmail(request.isAlarmStudyEnrollmentResult())
                .alarmStudyEnrollmentResultByEmail(request.isAlarmStudyUpdatedByEmail())
                .build();

        AccountDao newAccount = accountRepository.save(accountDao);

        sendCheckMail(newAccount);

        return newAccount.toDto();
    }

    private void sendCheckMail(AccountDao newAccount) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        newAccount.generateEmailCheckToken();
        mailMessage.setTo(newAccount.getEmail());
        mailMessage.setSubject("코스모, 회원 가입 인증");
        mailMessage.setText("/check-email-token?token=" + newAccount.getEmailCheckToken() +
                "&email=" + newAccount.getEmail());
        javaMailSender.send(mailMessage);
    }
}
