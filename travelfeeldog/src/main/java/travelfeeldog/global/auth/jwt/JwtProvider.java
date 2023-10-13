package travelfeeldog.global.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
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

    public String extractEmail(String token) {
        return (String) Jwts.parserBuilder().
                setSigningKey(jwtSecretKey.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("sub");
    }
}
