package travelfeeldog.global.auth.jwt;

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
import travelfeeldog.global.auth.jwt.exception.InvalidTokenException;

@RequiredArgsConstructor
@Service
@Slf4j
public class JwtProvider {

    private final JwtSecretKey jwtSecretKey;

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
        return createToken(payload, jwtSecretKey.getJwtValidityAccessTime(), "accessToken");
    }

    public Map<String, String> createRefreshToken(String payload) {
        Map<String, String> result = createToken(payload, jwtSecretKey.getJwtValidityRefreshTime(), "refreshToken");

        Date validityTime = new Date(new Date().getTime() + jwtSecretKey.getJwtValidityRefreshTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        String refreshTokenExpirationAt = simpleDateFormat.format(validityTime);

        result.put("refreshTokenExpirationAt", refreshTokenExpirationAt);
        return result;
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

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecretKey.getKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

}
