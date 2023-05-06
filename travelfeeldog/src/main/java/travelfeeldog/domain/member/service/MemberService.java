package travelfeeldog.domain.member.service;

import java.util.List;
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
    public Member saveMember(String nickName, String imageUrl, String token) {
        return memberRepository.saveMember(nickName, 1, 0, imageUrl, token)
                .orElseThrow(() -> new RuntimeException("Member not saved"));
    }

    @Transactional
    public void deleteMember(String token) {
        memberRepository.deleteMember(token);
    }

    @Transactional
    public Member updateImageUrl(String token, String imageUrl) {
        return memberRepository.updateMemberImageUrl(token, imageUrl);
    }

    @Transactional
    public Member updateNickName(String token, String nickName) {
        return memberRepository.updateNickName(token, nickName);
    }

    @Transactional
    public Member updateExpAndLevel(String token, int addExp) {
        return memberRepository.updateExpAndLevel(token, addExp);
    }

    public boolean isNickRedundant(String nickName) {
        return memberRepository.findByNickName(nickName).isPresent();
    }

    public boolean isTokenExist(String token) {
        return memberRepository.findByToken(token).isPresent();
    }

    public Member findById(int id) {
        return memberRepository.findById(id).orElseThrow(() -> new RuntimeException("Member not found"));
    }

    public Member findByToken(String token) {
        return memberRepository.findByToken(token).orElseThrow(() -> new RuntimeException("Member not found"));
    }

    public List<Member> getAll() {
        return memberRepository.findAll();
    }
}

