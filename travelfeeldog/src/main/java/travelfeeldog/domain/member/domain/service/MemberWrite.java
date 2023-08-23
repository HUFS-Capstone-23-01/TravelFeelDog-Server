package travelfeeldog.domain.member.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.domain.member.domain.model.Member;

import travelfeeldog.domain.member.domain.model.MemberNicknameHistory;
import travelfeeldog.domain.member.dto.MemberDtos.MemberPostRequestDto;
import travelfeeldog.domain.member.dto.MemberDtos.MemberPostResponseDto;

import travelfeeldog.domain.member.infrastructure.MemberNicknameHistoryRepository;
import travelfeeldog.domain.member.infrastructure.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberWrite implements MemberWriteService {

    private final MemberRepository memberRepository;

    private final MemberNicknameHistoryRepository memberNickNameHistoryRepository;

    @Override
    public MemberPostResponseDto create(MemberPostRequestDto requestDto) {
        var member =  memberRepository.save(requestDto.getNickName(), requestDto.getEmail(), 1, 0,
                        requestDto.getFirebaseToken())
                .orElseThrow(() -> new RuntimeException("Member not saved"));
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
