package travelfeeldog.global.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import travelfeeldog.global.auth.CustomUserDetailService;

@RequiredArgsConstructor
@Service
public class JwtProvider {

    private final JwtSecretKey jwtSecretKey;
    private CustomUserDetailService customUserDetailService;

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

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(
                this.extractEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "",
                userDetails.getAuthorities());
    }

    public String extractEmail(String token) {
        return (String) Jwts.parserBuilder().
                setSigningKey(jwtSecretKey.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("sub");
    }

    public String resolveToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null) {
            return null;
        }
        return header.replace("Bearer ", "");
    }

    public boolean validateJwtToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().
                    setSigningKey(jwtSecretKey.getKey())
                    .build()
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new IllegalArgumentException("유효하지 않은 토큰 정보입니다.");
        }
    }

    public Long getTokenExpireTime(String accessToken) {
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String[] parts = accessToken.split("\\.");
        ObjectMapper mapper = new ObjectMapper();
        String payload = new String(decoder.decode(parts[1]));

        try {
            return ((Number) mapper.readValue(payload, Map.class).get("exp")).longValue();
        } catch (IOException err) {
            throw new RuntimeException(err);
        }
    }
}
