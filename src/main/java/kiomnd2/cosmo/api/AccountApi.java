package kiomnd2.cosmo.api;

import kiomnd2.cosmo.config.security.jwt.JwtTokenProvider;
import kiomnd2.cosmo.dto.AccountDto;
import kiomnd2.cosmo.service.AccountService;
import kiomnd2.cosmo.validator.JoinValidator;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;


@RequiredArgsConstructor
@RestController
public class AccountApi {

    private final JoinValidator joinValidator;

    private final AccountService accountService;

    private final JwtTokenProvider<Long> jwtTokenProvider;

    @InitBinder("request")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(joinValidator);
    }

    @PostMapping("/api/v1/join")
    public JoinResponse join(@RequestBody @Valid AccountApi.JoinRequest request) {

        // 에러 시,, 처리
        AccountDto accountDto = accountService.processNewAccount(request);
        String token = jwtTokenProvider.createToken(accountDto.getId());

        return JoinResponse.builder()
                .token(token)
                .account(JoinResponse.Account.builder()
                        .id(accountDto.getId())
                        .nickname(accountDto.getNickname())
                        .email(accountDto.getEmail())
                        .joinAt(accountDto.getJoinAt())
                        .emailVerified(accountDto.isEmailVerified())
                        .emailCheckToken(accountDto.getEmailCheckToken())
                        .alarmStudyCreated(accountDto.isAlarmStudyCreatedByEmail())
                        .alarmStudyEnrollmentResult(accountDto.isAlarmStudyEnrollmentResultByEmail())
                        .alarmStudyUpdatedByEmail(accountDto.isAlarmStudyUpdatedByEmail())
                        .build())
                .build();
    }


    @Getter
    @ToString
    @Builder
    @AllArgsConstructor
    public static class JoinRequest {

        @NotBlank(message = "해당 값은 필수 입니다")
        @Length(min = 3, max = 20, message = "적절하지 않은 길이입니다")
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}$", message = "정상적이지 않은 닉네임 입니다")
        private String nickname;

        @Email(message = "이메일 형식에 맞춰주세요")
        @NotBlank(message = "해당 값을 필수 입니다")
        private String email;

        @NotBlank(message = "해당 값은 필수 입니다")
        @Length(min = 8, max = 20, message = "적절하지 않은 길이입니다")
        private String password;

        private boolean alarmStudyCreated;

        private boolean alarmStudyEnrollmentResult;

        private boolean alarmStudyUpdatedByEmail;

    }

    @Getter
    @ToString
    @Builder
    @AllArgsConstructor
    public static class JoinResponse {

        private String token;

        private JoinResponse.Account account;


        @Getter
        @ToString
        @AllArgsConstructor
        @Builder
        public static class Account {

            private Long id;

            private String email;

            private String nickname;

            private boolean emailVerified;

            private String emailCheckToken;

            private LocalDateTime joinAt;

            private String bio;

            private String url;

            private String occupation;

            private String location;

            private boolean alarmStudyCreated;

            private boolean alarmStudyEnrollmentResult;

            private boolean alarmStudyUpdatedByEmail;
        }
    }
}
