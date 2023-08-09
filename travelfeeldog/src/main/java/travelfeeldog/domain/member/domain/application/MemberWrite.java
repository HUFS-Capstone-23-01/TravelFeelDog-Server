package travelfeeldog.domain.member.domain.application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.domain.member.domain.model.Member;
import travelfeeldog.domain.member.dto.MemberDtos.MemberPostRequestDto;
import travelfeeldog.domain.member.infrastructure.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberWrite implements MemberWriteService {

    private final MemberRepository memberRepository;

    @Qualifier("memberReadService")
    private final MemberReadService memberReadService;

    @Override
    public Member save(MemberPostRequestDto requestDto) {
        return memberRepository.save(requestDto.getNickName(), requestDto.getEmail(), 1, 0,
                        requestDto.getFirebaseToken())
                .orElseThrow(() -> new RuntimeException("Member not saved"));
    }

    @Override
    public void deleteMember(String firebaseToken) {
        Member member = memberReadService.findByToken(firebaseToken);
        memberRepository.deleteMember(member);
    }

    @Override
    public Member updateImageUrl(String firebaseToken, String imageUrl) {
        Member member = memberReadService.findByToken(firebaseToken);
        member.updateMemberImageUrl(imageUrl);
        return member;
    }

    @Override
    public Member updateNickName(String firebaseToken, String nickName) {
        Member member = memberReadService.findByToken(firebaseToken);
        member.updateMemberNickName(nickName);
        return member;
    }

    @Override
    public Member updateExpAndLevel(String firebaseToken, int addExpValue) {
        Member member = memberReadService.findByToken(firebaseToken);
        member.updateExpAndLevel(addExpValue);
        return member;
    }

}
