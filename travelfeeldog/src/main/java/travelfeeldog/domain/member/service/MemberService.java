package travelfeeldog.domain.member.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import travelfeeldog.domain.member.dao.MemberRepository;
import travelfeeldog.domain.member.model.Member;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member saveMember(String nickName, String token) {
        String url = "https://tavelfeeldog.s3.ap-northeast-2.amazonaws.com/base/pic1.JPG";
        return memberRepository.saveMember(nickName, 1, 0, url, token)
                .orElseThrow(() -> new RuntimeException("Member not saved"));
    }

    @Transactional
    public void deleteMember(String firebaseToken) {
        memberRepository.deleteMember(firebaseToken);
    }

    @Transactional
    public Member updateImageUrl(String firebaseToken, String imageUrl) {
        return memberRepository.updateMemberImageUrl(firebaseToken, imageUrl);
    }

    @Transactional
    public Member updateNickName(String firebaseToken, String nickName) {
        return memberRepository.updateNickName(firebaseToken, nickName);
    }

    @Transactional
    public Member updateExpAndLevel(String firebaseToken, int addExpValue) {
        return memberRepository.updateExpAndLevel(firebaseToken, addExpValue);
    }

    public boolean isNickRedundant(String nickName) {
        return memberRepository.findByNickName(nickName).isPresent();
    }

    public boolean isTokenExist(String firebaseToken) {
        return memberRepository.findByToken(firebaseToken).isPresent();
    }

    public Optional<Member> findByNickName(String nickName) {
        return memberRepository.findByNickName(nickName);
    }

    public Member findByToken(String firebaseToken) {
        return memberRepository.findByToken(firebaseToken).orElseThrow(() -> new RuntimeException("Member not found"));
    }

    public List<Member> getAll() {
        return memberRepository.findAll();
    }
}

