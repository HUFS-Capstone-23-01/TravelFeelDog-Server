package travelfeeldog.global.auth.jwt.service;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.jsonwebtoken.Claims;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import travelfeeldog.global.auth.jwt.config.JwtSecretKey;
import travelfeeldog.global.auth.jwt.exception.InvalidTokenException;

class JwtProviderTest {
    String jwtSecretKey = "ThisIsASeTESTESTSETSTdkdkkskskkskskskkskskkskskaqqqqqq";
    @Test
    void validateExpiredToken_expired_check_test() {

        JwtProvider jwtProvider = new JwtProvider(new JwtSecretKey(jwtSecretKey, 0L, 0L));
        String payload = "11c7b743ff@gmail.com";
        String atk = jwtProvider.createAccessToken(payload).get("accessToken");
        assertNotNull(atk);
        assertFalse(atk.isEmpty());
        assertTrue(jwtProvider.isTokenExpire(atk));
        assertThrows(InvalidTokenException.class, () -> jwtProvider.validateToken(atk));
    }
    @Test
    void check_time_ExpiredToken() {
        long millSecondAccessTime  = 30000000L; // 8 시간 19분 59초

        JwtProvider jwtProvider = new JwtProvider(new JwtSecretKey(jwtSecretKey, millSecondAccessTime, 11111L));
        String payload = "11c7b743ff@gmail.com";

        String atk = jwtProvider.createAccessToken(payload).get("accessToken");
//        System.out.println(atk);

        Claims claims = jwtProvider.extractClaims(atk);

        // 만료 시간과 현재 시간을 가져와 남은 시간 계산
        Date expirationDate = claims.getExpiration();
        long millisUntilExpiration = expirationDate.getTime() - System.currentTimeMillis();
        long secondsUntilExpiration = millisUntilExpiration / 1000;
        long minutesUntilExpiration = secondsUntilExpiration / 60;
        long hoursUntilExpiration = minutesUntilExpiration / 60;

//        log.info("claims : {}", claims);
//        log.info("토큰이 만료되기까지 남은 시간: {}시간 {}분 {}초", hoursUntilExpiration, minutesUntilExpiration % 60, secondsUntilExpiration % 60);

        Assertions.assertEquals(secondsUntilExpiration%60,59);
        Assertions.assertEquals(minutesUntilExpiration % 60,19);
        Assertions.assertEquals(hoursUntilExpiration,8);
        assertNotNull(atk);

    }


}