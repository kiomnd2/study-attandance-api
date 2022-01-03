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

    private String password;

    private boolean emailVerified;

    private String emailCheckToken;

    private LocalDateTime joinAt;

    private String bio;

    private String url;

    private String occupation;

    private String location;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String profileImage;

    private boolean alarmStudyCreatedByEmail;

    private boolean alarmStudyEnrollmentResultByEmail;

    private boolean alarmStudyUpdatedByEmail;

    private LocalDateTime emailCheckTokenGeneratedAt;


    public void generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
    }

    public AccountDto toDto() {
        return AccountDto.builder()
                .id(this.id)
                .email(this.email)
                .nickname(this.nickname)
                .bio(this.bio)
                .emailCheckToken(this.emailCheckToken)
                .emailVerified(this.emailVerified)
                .joinAt(this.joinAt)
                .location(this.location)
                .occupation(this.occupation)
                .url(this.url)
                .alarmStudyCreatedByEmail(this.alarmStudyCreatedByEmail)
                .alarmStudyEnrollmentResultByEmail(this.alarmStudyEnrollmentResultByEmail)
                .alarmStudyUpdatedByEmail(this.alarmStudyUpdatedByEmail)
                .build();

    }
}
