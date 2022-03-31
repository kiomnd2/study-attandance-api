package kiomnd2.cosmo.config.security.jwt;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
@AllArgsConstructor
public class Token {

    private final String subject;

    private final Date issuedAt;

    private final Date expire;
}
