package kiomnd2.cosmo.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto {

    private Long id;

    private String token;

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
