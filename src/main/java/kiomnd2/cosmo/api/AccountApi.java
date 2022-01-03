package kiomnd2.cosmo.api;

import kiomnd2.cosmo.config.security.JwtTokenProvider;
import kiomnd2.cosmo.dto.AccountDto;
import kiomnd2.cosmo.service.AccountService;
import kiomnd2.cosmo.validator.JoinValidator;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
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

    private final JwtTokenProvider jwtTokenProvider;

    @InitBinder("request")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(joinValidator);
    }

    @PostMapping("/api/v1/join")
    public Response join(@RequestBody @Valid Request request) {

        // 에러 시,, 처리

        AccountDto accountDto = accountService.processNewAccount(request);
        //String token = jwtTokenProvider.createToken(accountDto.getId());

        return Response.builder()
                .token("")
                .account(Response.Account.builder()
                        .id(accountDto.getId())
                        .email(accountDto.getEmail())
                        .joinAt(accountDto.getJoinAt())
                        .bio(accountDto.getBio())
                        .emailVerified(accountDto.isEmailVerified())
                        .emailCheckToken(accountDto.getEmailCheckToken())
                        .occupation(accountDto.getOccupation())
                        .url(accountDto.getUrl())
                        .location(accountDto.getLocation())
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
    public static class Request {

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
    public static class Response {

        private String token;

        private Response.Account account;


        @Getter
        @ToString
        @AllArgsConstructor
        @Builder
        private static class Account {

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
