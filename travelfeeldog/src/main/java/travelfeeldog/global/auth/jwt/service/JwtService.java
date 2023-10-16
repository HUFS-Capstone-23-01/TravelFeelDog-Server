package travelfeeldog.global.auth.jwt.service;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.global.auth.jwt.response.TokenResponse;
import travelfeeldog.global.auth.jwt.exception.InvalidTokenException;
import travelfeeldog.member.domain.application.service.MemberRead;
import travelfeeldog.member.domain.model.Member;
import travelfeeldog.member.domain.model.Role;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class JwtService {
    private final JwtProvider jwtProvider;
    private final MemberRead memberReadService;
    public String findEmailByToken(String token) throws JwtException {
        try {
            Claims claims = jwtProvider.extractClaims(token);
            return claims.getSubject();
        } catch (RuntimeException e) {
            log.info("[ERROR] " + e);
            throw new InvalidTokenException("Invalid token", e);
        }
    }
    public Member findMemberByToken(String token) {
        String email = findEmailByToken(token);
        return memberReadService.findByEmail(email);
    }
    public TokenResponse updateToken(TokenResponse token,String email) {
        TokenResponse newToken = jwtProvider.updateToken(token,email);
        return new TokenResponse(newToken);
    }
    public void validateToken(String token){
        jwtProvider.validateToken(token);
    }
    public TokenResponse tokenUpdateCheck(String email) {
        Member member = memberReadService.findByEmail(email);
        if (member.getRole() != Role.GUEST) {
            TokenResponse token = new TokenResponse(member.getAccessToken(), member.getRefreshToken());
            token = updateToken(token, member.getEmail());
            member.updateToken(token.getAccessToken(), token.getRefreshToken());
            return token;
        }
        return null;
    }
    public String getAuthTokenByEmail(String email) {
        return jwtProvider.createAuthorizationToken(email);
    }

    public String getAccessTokenByEmail(String email) {
        Member member = memberReadService.findByEmail(email);
        if (member.getRole() != Role.GUEST) return member.getAccessToken();
        return "GUESTGUESTGUEST";
    }

    public String getRefreshTokenByEmail(String email) {
        Member member = memberReadService.findByEmail(email);
        if (member.getRole() != Role.GUEST) return member.getRefreshToken();
        return "GUESTGUESTGUEST";
    }

}
