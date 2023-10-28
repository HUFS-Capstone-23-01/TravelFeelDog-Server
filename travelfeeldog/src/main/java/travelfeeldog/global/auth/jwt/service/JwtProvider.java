package travelfeeldog.global.auth.jwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;
import travelfeeldog.global.auth.jwt.config.JwtSecretKey;
import travelfeeldog.global.auth.jwt.response.TokenResponse;
import travelfeeldog.global.auth.jwt.exception.InvalidTokenException;

@RequiredArgsConstructor
@Service
@Slf4j
public class JwtProvider {

    private final JwtSecretKey jwtSecretKey;
    private final static String AUTH_TOKEN_KEY = "authToken";
    private final static String ACCESS_TOKEN_KEY = "accessToken";
    private final static String REFRESH_TOKEN_KEY = "refreshToken";

    public Map<String, String> createToken(String payload, long validityDuration, String tokenKey) {
        Claims claims = Jwts.claims().setSubject(payload);
        Date now = new Date();
        Date validityTime = new Date(now.getTime() + validityDuration);
        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validityTime)
                .signWith(jwtSecretKey.getKey(), SignatureAlgorithm.HS256)
                .compact();

        Map<String, String> result = new HashMap<>();
        result.put(tokenKey, jwt);
        return result;
    }

    public Map<String, String> createAccessToken(String payload) {
        return createToken(payload, jwtSecretKey.getJwtValidityAccessTime(), ACCESS_TOKEN_KEY);
    }

    public String getNewAccessToken(String payload) {
        return createAccessToken(payload).get(ACCESS_TOKEN_KEY);
    }

    public Map<String, String> createRefreshToken(String payload) {
        Map<String, String> result = createToken(payload, jwtSecretKey.getJwtValidityRefreshTime(), REFRESH_TOKEN_KEY);
        Date validityTime = new Date(new Date().getTime() + jwtSecretKey.getJwtValidityRefreshTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        String refreshTokenExpirationAt = simpleDateFormat.format(validityTime);
        result.put("refreshTokenExpirationAt", refreshTokenExpirationAt);
        return result;
    }

    public String getNewRefreshToken(String payload) {
        return createRefreshToken(payload).get(REFRESH_TOKEN_KEY);
    }

    public Claims extractClaims(String token) throws JwtException {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(jwtSecretKey.getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new InvalidTokenException("Failed to extract claims from token", e);
        }
    }

    public void validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecretKey.getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
            throw new InvalidTokenException("Invalid JWT signature.", e);
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            throw new InvalidTokenException("Expired JWT token.", e);
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
            throw new InvalidTokenException("Unsupported JWT token.", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
            throw new InvalidTokenException("JWT token claims string is empty.", e);
        }
    }

    public boolean isTokenExpire(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtSecretKey.getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (JwtException e) {
            throw new InvalidTokenException("Failed to check expiration status of the token", e);
        }
    }

    public TokenResponse updateToken(TokenResponse token, String email) {
        String atk = token.accessToken();
        String rtk = token.refreshToken();

        if (isTokenExpire(atk)) {
            atk = getNewAccessToken(email);
        }
        if (isTokenExpire(rtk)) {
            rtk = getNewRefreshToken(email);
        }

        return new TokenResponse(atk, rtk);
    }

    public String createAuthorizationToken(String payload) {
        long fiveMin = 300000L;
        return createToken(payload, fiveMin, AUTH_TOKEN_KEY).get(AUTH_TOKEN_KEY);
    }

}
