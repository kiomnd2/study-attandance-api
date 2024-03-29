package kiomnd2.cosmo.config.security.filter;

import kiomnd2.cosmo.config.security.jwt.JwtTokenProvider;
import kiomnd2.cosmo.config.security.jwt.Token;
import kiomnd2.cosmo.exception.InvalidTokenException;
import kiomnd2.cosmo.exception.MemberNotFoundException;
import kiomnd2.cosmo.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider<Long> jwtTokenProvider;

    private final AccountService accountService;

    /**
     * 받은 토큰을 인증합니다.
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = getToken(request);
        log.debug("token = {}",token);

        // 토큰 체크
        if (!jwtTokenProvider.validateToken(token)) {
            log.debug("invalid token");
            throw new InvalidTokenException();
        }

        try {
            Token tokenInfo = jwtTokenProvider.getTokenInfo(token);
            String subject = tokenInfo.getSubject();
            log.debug("subject = {}", subject);


            chain.doFilter(request, response);
        } catch (MemberNotFoundException e) {
            // 토큰 포멧은 유효하고 사용자 아이디가 없는 경우
            throw new BadCredentialsException(e.getMessage());
        }
    }



    private String getToken(ServletRequest request) {
        return ((HttpServletRequest) request).getHeader(HttpHeaders.AUTHORIZATION);
    }
}
