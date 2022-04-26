package kiomnd2.cosmo.config;

import kiomnd2.cosmo.config.security.filter.JwtAuthenticationFilter;
import kiomnd2.cosmo.config.security.jwt.JwtTokenProvider;
import kiomnd2.cosmo.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider<Long> jwtTokenProvider;

    private final AccountService accountService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api/v*/join", "/api/v*/logged-in", "/check-email-token");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and().csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * JWT 토큰을 분석해서 인증을 하는 필터
     */
    private Filter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider, accountService);
    }
}
