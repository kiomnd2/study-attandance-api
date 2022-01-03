package kiomnd2.cosmo.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(of ="id") // 무한루프..
@Entity
public class AccountDao {

    @Id
    @GeneratedValue
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

    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String profileImage;

    private boolean studyCreatedByEmail;

    private boolean studyCreatedByWeb = true;

    private boolean studyEnrollmentResultByEmail;

    private boolean studyEnrollmentResultByWeb = true;

    private boolean studyUpdatedByEmail;

    private boolean studyUpdatedByWeb = true;

    private LocalDateTime emailCheckTokenGeneratedAt;


}
