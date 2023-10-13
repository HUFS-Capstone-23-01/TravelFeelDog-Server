package travelfeeldog.global.auth.jwt;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;
import travelfeeldog.global.auth.jwt.exception.InvalidTokenException;
import travelfeeldog.member.domain.application.service.MemberRead;
import travelfeeldog.member.domain.model.Member;

@RequiredArgsConstructor
@Service
public class JwtService {
    private final JwtProvider jwtProvider;
    private final MemberRead memberReadService;
    public String findEmailByToken(String token) throws JwtException {
        try {
            Claims claims = jwtProvider.extractClaims(token);
            return claims.getSubject();
        } catch (JwtException e) {
            throw new InvalidTokenException("Invalid token", e);
        }
    }
    public Member findMemberByToken(String token){
        String email = findEmailByToken(token);
        return memberReadService.findByEmail(email);
    }
}
