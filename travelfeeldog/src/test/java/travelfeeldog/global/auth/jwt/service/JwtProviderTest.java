package travelfeeldog.global.auth.jwt.service;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.jsonwebtoken.Claims;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import travelfeeldog.global.auth.jwt.config.JwtSecretKey;
import travelfeeldog.global.auth.jwt.exception.InvalidTokenException;

@Slf4j
class JwtProviderTest {
    @Test
    void validateExpiredToken_expired_check_test() {
        String jwtSecretKey = "ThisIsASecretKeyAndShouldBeAtLeast32Charsckdkdkkskskkskskskkskskkskskaqqqqqq";
        JwtProvider jwtProvider = new JwtProvider(new JwtSecretKey(jwtSecretKey, 0L, 0L));
        String payload = "11c7b743ff@gmail.com";

        String atk = jwtProvider.createAccessToken(payload).get("accessToken");
        log.info("atk : {}",atk);

        assertNotNull(atk);
        assertFalse(atk.isEmpty());

        assertTrue(jwtProvider.isTokenExpire(atk));
        // 토큰 유효성 검증에서 예외가 발생해야 합니다.
        assertThrows(InvalidTokenException.class, () -> jwtProvider.validateToken(atk));
    }
    @Test
    void check_time_ExpiredToken() {
        String jwtSecretKey = "ThisIsASecretKeyAndShouldBeAtLeast32Charsckdkdkkskskkskskskkskskkskskaqqqqqq";

        // 만료 시간을 0으로 설정하여 즉시 만료되는 토큰을 생성합니다.
        JwtProvider jwtProvider = new JwtProvider(new JwtSecretKey(jwtSecretKey, 1000L, 11111L));
        String payload = "11c7b743ff@gmail.com";

        String atk = jwtProvider.createAccessToken(payload).get("accessToken");
        System.out.println(atk);

        Claims claims = jwtProvider.extractClaims(atk);
        log.info("claims : {}",claims);
        // 만료 시간과 현재 시간을 가져와 남은 시간 계산
        Date expirationDate = claims.getExpiration();
        long millisUntilExpiration = expirationDate.getTime() - System.currentTimeMillis();
        long secondsUntilExpiration = millisUntilExpiration / 1000;
        long minutesUntilExpiration = secondsUntilExpiration / 60;
        long hoursUntilExpiration = minutesUntilExpiration / 60;

        // 로그에 남은 시간 출력
        log.info("claims : {}", claims);
        log.info("토큰이 만료되기까지 남은 시간: {}시간 {}분 {}초",
                hoursUntilExpiration, minutesUntilExpiration % 60, secondsUntilExpiration % 60);
        assertNotNull(atk);

    }


}