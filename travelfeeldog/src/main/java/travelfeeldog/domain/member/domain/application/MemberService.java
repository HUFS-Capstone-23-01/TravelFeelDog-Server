package travelfeeldog.domain.member.domain.application;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import travelfeeldog.domain.member.dto.MemberDtos.MemberPostRequestDto;
import travelfeeldog.domain.member.infrastructure.MemberRepository;
import travelfeeldog.domain.member.domain.model.Member;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member saveMember(MemberPostRequestDto requestDto) {
        return memberRepository.saveMember(requestDto.getNickName(), requestDto.getEmail(),1, 0, requestDto.getFirebaseToken())
            .orElseThrow(() -> new RuntimeException("Member not saved"));
    }

    public Member findByNickName(String nickName) {
        return memberRepository.findByNickName(nickName).orElseThrow(
            () -> new NoSuchElementException("Member not found by nickName :" + nickName));
    }

    public Member findByToken(String firebaseToken) {
        return memberRepository.findByToken(firebaseToken).orElseThrow(
            () -> new NoSuchElementException("Member not found by token:" + firebaseToken));
    }

    public boolean isNickRedundant(String nickName) {
        return memberRepository.findByNickName(nickName).isPresent();
    }

    public boolean isTokenExist(String firebaseToken) {
        return memberRepository.findByToken(firebaseToken).isPresent();
    }

    public List<Member> getAll() {
        return memberRepository.findAll();
    }

    @Transactional
    public void deleteMember(String firebaseToken) {
        Member member = findByToken(firebaseToken);
        memberRepository.deleteMember(member);
    }

    @Transactional
    public Member updateImageUrl(String firebaseToken, String imageUrl) {
        Member member = findByToken(firebaseToken);
        member.updateMemberImageUrl(imageUrl);
        return member;
    }

    @Transactional
    public Member updateNickName(String firebaseToken, String nickName) {
        Member member = findByToken(firebaseToken);
        member.updateMemberNickName(nickName);
        return member;
    }

    @Transactional
    public Member updateExpAndLevel(String firebaseToken, int addExpValue) {
        Member member = findByToken(firebaseToken);
        member.updateExpAndLevel(addExpValue);
        return member;
    }

}

