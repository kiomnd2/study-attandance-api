package kiomnd2.cosmo.config.security.jwt;

public interface JwtTokenProvider<T> {
    String createToken(T subject);
    Token getTokenInfo(String token);
    boolean validateToken(String token);
}
