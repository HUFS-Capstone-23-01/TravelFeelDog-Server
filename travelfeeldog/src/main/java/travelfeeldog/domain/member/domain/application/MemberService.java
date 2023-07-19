package travelfeeldog.domain.member.domain.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import travelfeeldog.domain.member.infrastructure.MemberRepository;
import travelfeeldog.domain.member.domain.model.Member;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member saveMember(String nickName, String token) {
        return memberRepository.saveMember(nickName, 1, 0, token)
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
        return memberRepository.updateMemberImageUrl(member, imageUrl);
    }

    @Transactional
    public Optional<Member> updateNickName(String firebaseToken, String nickName) {
        Member member = findByToken(firebaseToken);
        if(memberRepository.findByNickName(nickName).isPresent()) {
            return Optional.empty();
        }
        else {
            member.setNickName(nickName);
            return Optional.of(member);
        }
    }

    @Transactional
    public Member updateExpAndLevel(String firebaseToken, int addExpValue) {
        Member member = findByToken(firebaseToken);
        return memberRepository.updateExpAndLevel(member, addExpValue);
    }
}

