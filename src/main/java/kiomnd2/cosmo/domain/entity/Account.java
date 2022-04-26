package kiomnd2.cosmo.domain.entity;

import kiomnd2.cosmo.dto.AccountDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


@RequiredArgsConstructor
@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode(of ="id") // 무한루프..
@Entity
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private String nickname;

    private boolean emailVerified;

    private String emailCheckToken;

    private LocalDateTime joinAt;

    private boolean alarmStudyCreatedByEmail;

    private boolean alarmStudyEnrollmentResultByEmail;

    private boolean alarmStudyUpdatedByEmail;

    private LocalDateTime emailCheckTokenGeneratedAt;


    public void generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
        this.emailCheckTokenGeneratedAt = LocalDateTime.now();
    }

    public boolean isValidToken(String token) {
        return token.equals(this.emailCheckToken);
    }

    public void completeJoin() {
        this.joinAt = LocalDateTime.now();
        this.emailVerified = true;
    }


    public AccountDto toDto() {
        return AccountDto.builder()
                .id(this.id)
                .email(this.email)
                .nickname(this.nickname)
                .emailCheckToken(this.emailCheckToken)
                .emailVerified(this.emailVerified)
                .joinAt(this.joinAt)
                .alarmStudyCreatedByEmail(this.alarmStudyCreatedByEmail)
                .alarmStudyEnrollmentResultByEmail(this.alarmStudyEnrollmentResultByEmail)
                .alarmStudyUpdatedByEmail(this.alarmStudyUpdatedByEmail)
                .build();

    }
}
