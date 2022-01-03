package kiomnd2.cosmo.api;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RequiredArgsConstructor
@RestController
public class AccountApi {

    @PostMapping("/api/v1/join")
    public Response join(@RequestBody Request request) {

        return Response.builder().build();
    }


    @Getter
    @ToString
    @Builder
    @AllArgsConstructor
    public static class Request {

        private String nickname;

        private String email;

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

        private boolean alarmStudyCreated;

        private boolean alarmStudyEnrollmentResult;

        private boolean alarmStudyUpdatedByEmail;

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
        }
    }
}
