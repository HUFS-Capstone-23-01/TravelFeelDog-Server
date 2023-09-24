package travelfeeldog.aggregate.member.domain.application.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.aggregate.member.domain.model.MemberNicknameHistory;
import travelfeeldog.aggregate.member.dto.MemberDto;
import travelfeeldog.aggregate.member.dto.MemberNickNameHistoryDto;
import travelfeeldog.aggregate.member.repository.MemberNicknameHistoryRepository;
import travelfeeldog.aggregate.member.domain.model.Member;
import travelfeeldog.aggregate.member.dto.MemberDtos.MemberResponse;

import travelfeeldog.aggregate.member.repository.MemberRepository;

@Service("memberReadService")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberRead implements MemberReadService {

    private final MemberRepository memberRepository;
    private final MemberNicknameHistoryRepository memberNickNameHistoryRepository;

    @Override
    public MemberDto getMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Member not found by memberId" + memberId));
        return toDto(member);
    }

    @Override
    public List<MemberDto> getMembers(List<Long> memberIds) {
        return memberRepository.findAllByIdIn(memberIds).stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public Member findByNickName(String nickName) {
        return memberRepository.findByNickName(nickName)
                .orElseThrow(() -> new NoSuchElementException(
                        "Member not found by nickName :" + nickName));
    }

    @Override  // fix point
    public Member findByToken(String firebaseToken) {
//        return memberRepository.findByToken(firebaseToken) // add access exchange logi
//                .orElseThrow(() -> new NoSuchElementException(
//                        "Member not found by token:" + firebaseToken));
        return null;
    }
    @Override  // fix point
    public boolean isTokenExist(String firebaseToken) {
        return true;
//        return memberRepository.findByToken(firebaseToken).isPresent();
    }
    @Override
    public boolean isNickRedundant(String nickName) {
        return memberRepository.findByNickName(nickName).isPresent();
    }
    public List<Member> getAll() {
        return memberRepository.findAll();
    }
    @Override
    public List<MemberResponse> getAllMembers() {
        return getAll().stream().map(MemberResponse::new).collect(Collectors.toList());
    }

    @Override
    public List<MemberNickNameHistoryDto> getAllMemberHistory(Long memberId) {
        return memberNickNameHistoryRepository.findAllByMemberId(memberId).stream()
                .map(this::toDto)
                .toList();
    }

    public MemberDto toDto(Member member) {
        return new MemberDto(member.getId(), member.getNickName(), member.getEmail());
    }

    private MemberNickNameHistoryDto toDto(MemberNicknameHistory history) {
        return new MemberNickNameHistoryDto(
                history.getId(),
                history.getMemberId(),
                history.getNickName(),
                history.getCreatedDateTime()
        );
    }
}



