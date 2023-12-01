package travelfeeldog.member.application.service;

import travelfeeldog.global.auth.jwt.response.TokenResponse;
import travelfeeldog.infra.oauth2.dto.OAuthAttributes;
import travelfeeldog.member.domain.model.Member;
import travelfeeldog.member.dto.MemberDtos.MemberPostRequestDto;
import travelfeeldog.member.dto.MemberDtos.MemberPostResponseDto;

public interface MemberWriteService {
    MemberPostResponseDto create(MemberPostRequestDto requestDto, TokenResponse tokenResponse);

    MemberPostResponseDto createWithPassword(MemberPostRequestDto requestDto, TokenResponse tokenResponse);

    void deleteMember(Member member);

    Member updateImageUrl(Member member, String imageUrl);

    Member updateNickName(Member member, String nickName);

    Member updateExpAndLevel(Member member, int addExpValue);

    void save(Member member);

    Member saveByAttributes(OAuthAttributes attributes);
}
