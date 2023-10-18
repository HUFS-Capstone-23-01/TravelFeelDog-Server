package travelfeeldog.member.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.global.auth.jwt.response.TokenResponse;
import travelfeeldog.infra.oauth2.dto.OAuthAttributes;
import travelfeeldog.member.domain.model.MemberNicknameHistory;
import travelfeeldog.member.repository.MemberNicknameHistoryRepository;
import travelfeeldog.member.domain.model.Member;

import travelfeeldog.member.dto.MemberDtos.MemberPostRequestDto;
import travelfeeldog.member.dto.MemberDtos.MemberPostResponseDto;

import travelfeeldog.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberWrite implements MemberWriteService {

    private final MemberRepository memberRepository;

    private final MemberNicknameHistoryRepository memberNickNameHistoryRepository;
    public void save(Member member){
        memberRepository.save(member).orElseThrow(()->new IllegalStateException(""));
    }

    @Override
    public Member saveByAttributes(OAuthAttributes attributes) {
        Member member = memberRepository.findMemberForLogin(attributes.getEmail())
                .orElse(attributes.toEntity());
        save(member);
        return member;
    }
    @Override
    public MemberPostResponseDto create(MemberPostRequestDto requestDto,
            TokenResponse tokenResponse) {
        var member = memberRepository.save(requestDto.getEmail(), tokenResponse.getAccessToken(),
                tokenResponse.getRefreshToken());
        saveNickNameHistory(member);
        return new MemberPostResponseDto(member);
    }

    @Override
    public void deleteMember(Member member) {
        memberRepository.deleteMember(member);
    }

    @Override
    public Member updateImageUrl(Member member, String imageUrl) {
        member.updateMemberImageUrl(imageUrl);
        return member;
    }

    @Override
    public Member updateNickName(Member member, String nickName) {
        member.updateMemberNickName(nickName);
        saveNickNameHistory(member);
        return member;
    }

    private void saveNickNameHistory(Member member) {
        var histroy = MemberNicknameHistory
                .builder()
                .id(member.getId())
                .nickName(member.getNickName())
                .build();
        memberNickNameHistoryRepository.save(histroy);
    }

    @Override
    public Member updateExpAndLevel(Member member, int addExpValue) {
        member.updateExpAndLevel(addExpValue);
        return member;
    }

}
