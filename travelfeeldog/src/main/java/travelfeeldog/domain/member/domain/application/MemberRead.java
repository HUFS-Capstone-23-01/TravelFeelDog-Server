package travelfeeldog.domain.member.domain.application;

import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.domain.member.domain.model.Member;

import travelfeeldog.domain.member.domain.model.MemberNickNameHistory;
import travelfeeldog.domain.member.dto.MemberNickNameHistoryDto;
import travelfeeldog.domain.member.infrastructure.MemberNickNameHistoryRepository;
import travelfeeldog.domain.member.infrastructure.MemberRepository;

@Service("memberReadService")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberRead implements MemberReadService {

    private final MemberRepository memberRepository;
    private final MemberNickNameHistoryRepository memberNickNameHistoryRepository;

    @Override
    public Member findByNickName(String nickName) {
        return memberRepository.findByNickName(nickName).orElseThrow(
                () -> new NoSuchElementException("Member not found by nickName :" + nickName));
    }

    @Override
    public Member findByToken(String firebaseToken) {
        return memberRepository.findByToken(firebaseToken).orElseThrow(
                () -> new NoSuchElementException("Member not found by token:" + firebaseToken));
    }

    @Override
    public boolean isNickRedundant(String nickName) {
        return memberRepository.findByNickName(nickName).isPresent();
    }

    @Override
    public boolean isTokenExist(String firebaseToken) {
        return memberRepository.findByToken(firebaseToken).isPresent();
    }

    @Override
    public List<Member> getAll() {
        return memberRepository.findAll();
    }
    @Override
    public List<MemberNickNameHistoryDto> getAllMemberHistory(Long memberId) {
        return memberNickNameHistoryRepository.findAllByMemberId(memberId)
                .stream()
                .map(this::toDto)
                .toList();
    }
    private MemberNickNameHistoryDto toDto(MemberNickNameHistory history){
        return new MemberNickNameHistoryDto(
                history.getId(),
                history.getMemberId(),
                history.getNickName(),
                history.getCreatedDateTime()
        );
    }
}



