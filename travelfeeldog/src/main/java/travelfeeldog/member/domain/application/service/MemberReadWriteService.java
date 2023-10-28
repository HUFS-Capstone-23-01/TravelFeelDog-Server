package travelfeeldog.member.domain.application.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import travelfeeldog.global.auth.jwt.service.JwtProvider;
import travelfeeldog.global.auth.jwt.response.TokenResponse;
import travelfeeldog.infra.oauth2.dto.OAuthAttributes;
import travelfeeldog.member.dto.MemberDto;
import travelfeeldog.member.dto.MemberDtos.*;
import travelfeeldog.member.dto.MemberNickNameHistoryDto;
import travelfeeldog.member.domain.model.Member;

@Service
@RequiredArgsConstructor
public class MemberReadWriteService implements MemberService {

    @Qualifier("memberReadService")
    private final MemberReadService memberReadService;

    @Qualifier("memberWrite")
    private final MemberWriteService memberWriteService;

    private final JwtProvider jwtProvider;

    @Override
    public Member findByToken(String firebaseToken) {
        return memberReadService.findByToken(firebaseToken);
    }

    @Override
    public MemberDto getMember(Long memberId) {
        return memberReadService.getMember(memberId);
    }

    @Override
    public List<MemberDto> getMembers(List<Long> followingMemberIds) {
        return memberReadService.getMembers(followingMemberIds);
    }

    @Override
    public Member getByEmail(OAuthAttributes attributes) {
        return memberReadService.getByEmail(attributes);
    }

    @Override
    public Member findByEmail(String email) {
        return memberReadService.findByEmail(email);
    }

    @Override
    public Member findByNickName(String nickName) {
        return memberReadService.findByNickName(nickName);
    }

    @Override
    public boolean isNickRedundant(String nickName) {
        return memberReadService.isNickRedundant(nickName);
    }

    @Override
    public List<MemberResponse> getAllMembers() {
        return memberReadService.getAllMembers();
    }

    @Override
    public List<MemberNickNameHistoryDto> getAllMemberHistory(Long memberId) {
        return memberReadService.getAllMemberHistory(memberId);
    }

    /*
     Member Write Service
     */
    public MemberRegisterResponse register(MemberPostRequestDto requestDto) {
        TokenResponse tokenResponse = createTokenReturn(requestDto);
        MemberPostResponseDto result = create(requestDto, tokenResponse);
        return new MemberRegisterResponse(result, tokenResponse);
    }

    private TokenResponse createTokenReturn(MemberPostRequestDto request) {
        String accessToken = jwtProvider.getNewAccessToken(request.getEmail());
        String refreshToken = jwtProvider.getNewRefreshToken(request.getEmail());
        return new TokenResponse(accessToken, refreshToken);
    }

    @Override
    public MemberPostResponseDto create(MemberPostRequestDto requestDto, TokenResponse tokenResponse) {
        return memberWriteService.create(requestDto, tokenResponse);
    }

    @Override
    public void deleteMember(Member member) {
        memberWriteService.deleteMember(member);
    }

    @Override
    public Member updateImageUrl(Member member, String imageUrl) {
        return memberWriteService.updateImageUrl(member, imageUrl);
    }

    @Override
    public Member updateNickName(Member member, String nickName) {
        return memberWriteService.updateNickName(member, nickName);
    }

    @Override
    public Member updateExpAndLevel(Member member, int addExpValue) {
        return memberWriteService.updateExpAndLevel(member, addExpValue);
    }

    @Override
    public void save(Member member) {
        memberWriteService.save(member);
    }

    @Override
    public Member saveByAttributes(OAuthAttributes attributes) {
        return memberWriteService.saveByAttributes(attributes);
    }
}
