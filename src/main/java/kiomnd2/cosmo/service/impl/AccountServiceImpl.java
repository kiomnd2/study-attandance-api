package kiomnd2.cosmo.service.impl;

import kiomnd2.cosmo.api.AccountApi;
import kiomnd2.cosmo.config.mail.EmailMessage;
import kiomnd2.cosmo.config.mail.EmailService;
import kiomnd2.cosmo.domain.entity.Account;
import kiomnd2.cosmo.dto.AccountDto;
import kiomnd2.cosmo.exception.InvalidTokenException;
import kiomnd2.cosmo.exception.NotFoundEmailException;
import kiomnd2.cosmo.repository.AccountRepository;
import kiomnd2.cosmo.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final EmailService emailService;

    @Transactional(readOnly = false)
    @Override
    public AccountDto getAccount(AccountApi.JoinRequest request) {

        // 아이디 찾아서 없으면 등록
        Account newAccount = accountRepository.findById(request.getId()).orElseGet(() -> {
            Account account = Account.builder()
                    .id(request.getId())
                    .nickname(request.getNickname())
                    .email(request.getEmail())
                    .alarmStudyUpdatedByEmail(request.isAlarmStudyCreated())
                    .alarmStudyEnrollmentResultByEmail(request.isAlarmStudyEnrollmentResult())
                    .alarmStudyEnrollmentResultByEmail(request.isAlarmStudyUpdatedByEmail())
                    .build();
            sendCheckMail(account);
            return accountRepository.save(account);
        });


        return newAccount.toDto();
    }

    private void sendCheckMail(Account newAccount) {
        newAccount.generateEmailCheckToken();
        EmailMessage mailMessage = EmailMessage
                .builder()
                .to(newAccount.getEmail())
                .subject("코스모, 회원가입 인증")
                .message("/api/v1/check/email?token=" + newAccount.getEmailCheckToken() +
                                        "&email=" + newAccount.getEmail()).build();
        emailService.sendEmail(mailMessage);
    }

    @Transactional(readOnly = false)
    @Override
    public AccountDto checkToken(String token, Long id) {
        Account account = accountRepository.findById(id).orElseThrow(NotFoundEmailException::new);
        if (!account.checkToken(token)) {
            throw new InvalidTokenException();
        }
        account.completeJoin();

        return account.toDto();
    }
}
