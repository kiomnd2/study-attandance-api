package kiomnd2.cosmo.config.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtTokenRole {
    /**
     * 기본 롤
     */
    public static final String ROLE_USER = "ROLE_USER";
    /**
     * 관리자 롤
     */
    public static final String ROLE_MANAGER = "ROLE_MANAGER";


}
