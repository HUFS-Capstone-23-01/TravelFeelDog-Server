package travelfeeldog.global.auth.jwt.service;

import io.jsonwebtoken.Claims;

import lombok.RequiredArgsConstructor;

import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.global.auth.jwt.response.TokenResponse;
import travelfeeldog.global.auth.jwt.exception.InvalidTokenException;
import travelfeeldog.infra.oauth2.api.TokenLoginResponse;
import travelfeeldog.member.domain.application.service.MemberReadService;
import travelfeeldog.member.domain.model.Member;
import travelfeeldog.member.domain.model.Role;

@RequiredArgsConstructor
@Service
@Transactional
public class JwtService {
    private final JwtProvider jwtProvider;
    private final MemberReadService memberReadService;
    private final static String GUEST_TOKEN = "GUESTGUESTGUEST";
    public String findEmailByToken(String token) throws JwtException {
        try {
            Claims claims = jwtProvider.extractClaims(token);
            return claims.getSubject();
        } catch (RuntimeException e) {
            throw new InvalidTokenException("Invalid token", e);
        }
    }
    public TokenLoginResponse getTokenLoginResponseByMember(Member member) {
        String atk = GUEST_TOKEN;
        String rtk = GUEST_TOKEN;
        if (member.getRole() != Role.GUEST){
            atk = member.getAccessToken();
            rtk = member.getRefreshToken();
        }

        return new TokenLoginResponse(member.getEmail(),member.getRole().getKey(),new TokenResponse(atk,rtk));
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

}
