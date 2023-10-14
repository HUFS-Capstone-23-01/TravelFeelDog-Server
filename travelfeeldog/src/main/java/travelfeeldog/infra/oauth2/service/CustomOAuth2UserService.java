package travelfeeldog.infra.oauth2.service;

import java.util.Collections;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import org.springframework.transaction.annotation.Transactional;

import travelfeeldog.global.auth.jwt.JwtService;
import travelfeeldog.global.auth.jwt.TokenResponse;
import travelfeeldog.member.domain.model.Member;
import travelfeeldog.member.domain.model.Role;
import travelfeeldog.member.repository.MemberRepository;
import travelfeeldog.infra.oauth2.dto.OAuthAttributes;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,
                oAuth2User.getAttributes());
        Member member = getByEmail(attributes);
        tokenUpdateCheck(member);
        saveOrUpdate(member);
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private void tokenUpdateCheck(Member member) {
        if (member.getRole() != Role.GUEST) {
            TokenResponse token = new TokenResponse(member.getAccessToken(),
                    member.getRefreshToken());
            token = jwtService.updateToken(token, member.getEmail());
            member.updateToken(token.getAccessToken(), token.getRefreshToken());
        }
    }

    public void saveOrUpdate(Member user) {
        memberRepository.save(user)
                .orElseThrow(() -> new IllegalArgumentException("Save Or Update Error"));
    }

    public Member getByEmail(OAuthAttributes attributes) {
        return memberRepository.findMemberForLogin(attributes.getEmail())
                .orElse(attributes.toEntity());
    }
}
