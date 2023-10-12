package travelfeeldog.global.auth.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtSecretKeyConfiguration {
    @Value("${security.jwt.secretKey}")
    private String jwtSecretKey;

    @Value("${security.jwt.validityAccessTime}")
    private Long jwtValidityAccessTime;

    @Value("${security.jwt.validityRefreshTime}")
    private Long jwtValidityRefreshTime;

    @Bean
    public JwtSecretKey jwtSecretKey() {
        return new JwtSecretKey(jwtSecretKey, jwtValidityAccessTime, jwtValidityRefreshTime);
    }
}
