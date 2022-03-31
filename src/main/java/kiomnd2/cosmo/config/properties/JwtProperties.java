package kiomnd2.cosmo.config.properties;

import kiomnd2.cosmo.config.security.jwt.JwtConst;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.time.Duration;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "jwt")
@ConstructorBinding
public class JwtProperties {

    private final String secretKey;

    private final Duration duration = Duration.ofMinutes(JwtConst.TOKEN_VALIDATION_TIME);
}
