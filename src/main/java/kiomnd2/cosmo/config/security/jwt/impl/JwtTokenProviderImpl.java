package kiomnd2.cosmo.config.security.jwt.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kiomnd2.cosmo.config.properties.JwtProperties;
import kiomnd2.cosmo.config.security.jwt.Token;
import kiomnd2.cosmo.config.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class JwtTokenProviderImpl implements JwtTokenProvider<Long> {

    private final JwtProperties jwtProperties;

    @Override
    public String createToken(Long subject) {
        Claims claims = Jwts.claims();
        claims.setSubject(Long.toString(subject));

        Instant nowInstant = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(nowInstant))
                .setExpiration(Date.from(nowInstant.plus(jwtProperties.getDuration())))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    @Override
    public Token getTokenInfo(String token) {
        Claims body = getClaims(token);
        return new Token(body.getSubject(), body.getIssuedAt(), body.getExpiration());
    }

    @Override
    public boolean validateToken(String token) {
        if (token == null) return false;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(token);
        return claimsJws.getBody().getExpiration().before(new Date());
    }

    /**
     * token에서 subject 추출
     */
    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token).getBody();
    }

}
