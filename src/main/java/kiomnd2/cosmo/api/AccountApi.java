package kiomnd2.cosmo.api;

import kiomnd2.cosmo.config.security.jwt.JwtTokenProvider;
import kiomnd2.cosmo.dto.AccountDto;
import kiomnd2.cosmo.service.AccountService;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;


@RequiredArgsConstructor
@RestController
public class AccountApi {


    private final AccountService accountService;

    private final JwtTokenProvider<Long> jwtTokenProvider;

    @PostMapping("/api/v1/logged-in")
    public Response<JoinResponse> loggedIn(@RequestBody @Valid AccountApi.JoinRequest request ) {
        AccountDto account = accountService.getAccount(request);
        String token = jwtTokenProvider.createToken(account.getId());

        return Response.success(JoinResponse.builder()
                .token(token)
                .account(JoinResponse.Account.builder()
                        .id(account.getId())
                        .nickname(account.getNickname())
                        .email(account.getEmail())
                        .joinAt(account.getJoinAt())
                        .emailVerified(account.isEmailVerified())
                        .emailCheckToken(account.getEmailCheckToken())
                        .alarmStudyCreated(account.isAlarmStudyCreatedByEmail())
                        .alarmStudyEnrollmentResult(account.isAlarmStudyEnrollmentResultByEmail())
                        .alarmStudyUpdatedByEmail(account.isAlarmStudyUpdatedByEmail())
                        .build())
                .build());
    }

    @PostMapping("/api/v1/join")
    public Response<JoinResponse> join(@RequestBody @Valid AccountApi.JoinRequest request) {

        // 에러 시,, 처리
        AccountDto accountDto = accountService.createAccount(request);


        return Response.success(JoinResponse.builder()
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
                .build());
    }



    @Getter
    @ToString
    @Builder
    @AllArgsConstructor
    public static class JoinRequest {
        private Long id;

        @NotBlank(message = "해당 값은 필수 입니다")
        @Length(min = 3, max = 20, message = "적절하지 않은 길이입니다")
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}$", message = "정상적이지 않은 닉네임 입니다")
        private String nickname;

        @Email(message = "이메일 형식에 맞춰주세요")
        @NotBlank(message = "해당 값을 필수 입니다")
        private String email;

        private boolean alarmStudyCreated;

        private boolean alarmStudyEnrollmentResult;

        private boolean alarmStudyUpdatedByEmail;
    }

    @Getter
    @ToString
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class JoinResponse {

        private JoinResponse.Account account;

        private String token;

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

            private boolean alarmStudyCreated;

            private boolean alarmStudyEnrollmentResult;

            private boolean alarmStudyUpdatedByEmail;
        }
    }
}
