package travelfeeldog.global.auth.jwt.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.global.auth.jwt.exception.InvalidTokenException;
import travelfeeldog.global.auth.jwt.response.TokenResponse;
import travelfeeldog.infra.oauth2.api.TokenLoginResponse;
import travelfeeldog.member.domain.application.service.MemberReadService;
import travelfeeldog.member.domain.model.Member;

@RequiredArgsConstructor
@Service
@Transactional
public class JwtService {
    private final JwtProvider jwtProvider;
    private final MemberReadService memberReadService;
    private final static String GUEST_TOKEN = "GUESTGUESTGUEST";

    public String findEmailByExtractToken(String token) throws JwtException {
        try {
            Claims claims = jwtProvider.extractClaims(token);
            return claims.getSubject();
        } catch (RuntimeException e) {
            throw new InvalidTokenException("Invalid token", e);
        }
    }

    public Member findMemberByToken(String token) {
        String email = findEmailByExtractToken(token);
        return memberReadService.findByEmail(email);
    }

    public TokenLoginResponse getTokenLoginResponseByMember(Member member) {
        TokenResponse tokenResponse = tokenUpdateCheckByMember(member);
        return new TokenLoginResponse(member.getEmail(), member.getRole().getKey(), tokenResponse);
    }

    public TokenResponse updateToken(TokenResponse token, String email) {
        return jwtProvider.updateToken(token, email);
    }

    public void validateToken(String token) {
        jwtProvider.validateToken(token);
    }

    private TokenResponse tokenUpdateCheckByMember(Member member) {
        if (member.isNotGuest()) {
            TokenResponse memberToken = new TokenResponse(member.getAccessToken(), member.getRefreshToken());
            memberToken = updateToken(memberToken, member.getEmail());
            member.updateToken(memberToken.accessToken(), memberToken.refreshToken());
            return memberToken;
        }
        return new TokenResponse(GUEST_TOKEN, GUEST_TOKEN);
    }

    public void tokenUpdateCheckByEmail(String email) {
        Member member = memberReadService.findByEmail(email);
        tokenUpdateCheckByMember(member);
    }

    public String getNewAuthTokenByEmail(String email) {
        return jwtProvider.createAuthorizationToken(email);
    }

}
