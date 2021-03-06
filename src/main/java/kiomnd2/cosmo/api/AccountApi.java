package kiomnd2.cosmo.api;

import kiomnd2.cosmo.config.security.jwt.JwtTokenProvider;
import kiomnd2.cosmo.dto.AccountDto;
import kiomnd2.cosmo.service.AccountService;
import kiomnd2.cosmo.validator.JoinValidator;
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

    private final JoinValidator joinValidator;

    private final AccountService accountService;

    private final JwtTokenProvider<Long> jwtTokenProvider;

    @InitBinder("request")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(joinValidator);
    }

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

        // ?????? ???,, ??????
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

        @NotBlank(message = "?????? ?????? ?????? ?????????")
        @Length(min = 3, max = 20, message = "???????????? ?????? ???????????????")
        @Pattern(regexp = "^[???-??????-???a-z0-9_-]{3,20}$", message = "??????????????? ?????? ????????? ?????????")
        private String nickname;

        @Email(message = "????????? ????????? ???????????????")
        @NotBlank(message = "?????? ?????? ?????? ?????????")
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
