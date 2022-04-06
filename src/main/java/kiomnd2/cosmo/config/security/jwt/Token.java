package kiomnd2.cosmo.config.security.jwt;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
@Getter
public class Token {

    private final String subject;

    private final Date issuedAt;

    private final Date expire;
}
