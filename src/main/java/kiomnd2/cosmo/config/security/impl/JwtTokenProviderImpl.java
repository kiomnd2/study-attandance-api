package kiomnd2.cosmo.config.security.impl;

import kiomnd2.cosmo.config.security.JwtTokenProvider;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProviderImpl implements JwtTokenProvider {
    @Override
    public String createToken(Object subject) {
        return null;
    }
}
