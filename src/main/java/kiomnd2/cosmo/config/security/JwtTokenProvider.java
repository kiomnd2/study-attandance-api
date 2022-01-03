package kiomnd2.cosmo.config.security;

public interface JwtTokenProvider {
    String createToken(Object subject);
}
