package travelfeeldog.member.application.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.infra.oauth2.dto.OAuthAttributes;
import travelfeeldog.member.domain.model.MemberNicknameHistory;
import travelfeeldog.member.domain.model.Role;
import travelfeeldog.member.dto.MemberDto;
import travelfeeldog.member.dto.MemberDtos.MemberLoginRequestDto;
import travelfeeldog.member.dto.MemberNickNameHistoryDto;
import travelfeeldog.member.domain.MemberNicknameHistoryRepository;
import travelfeeldog.member.domain.model.Member;
import travelfeeldog.member.dto.MemberDtos.MemberResponse;

import travelfeeldog.member.domain.MemberRepository;

@Service("memberReadService")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberRead implements MemberReadService {

    private final MemberRepository memberRepository;
    private final MemberNicknameHistoryRepository memberNickNameHistoryRepository;

    @Override
    public MemberDto getMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("Member not found by memberId" + memberId));
        return toDto(member);
    }

    @Override
    public List<MemberDto> getMembers(List<Long> memberIds) {
        return memberRepository.findAllByIdIn(memberIds).stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public Member findByToken(String accessToken) {
        return null;
    }

    @Override
    public Member findByNickName(String nickName) {
        return memberRepository.findByNickName(nickName)
                .orElseThrow(() -> new NoSuchElementException("Member not found by nickName :" + nickName));
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("no member find by email"));
    }

    @Override
    public Member loginWithPasswordRequest(MemberLoginRequestDto request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalStateException("no member find by email"));
        member.checkPassword(request.getPassWord());
        return member;
    }

    @Override
    public boolean isNickRedundant(String nickName) {
        Optional<Member> member = memberRepository.findByNickName(nickName);
        return member.map(value -> value.getRole() != Role.GUEST).orElse(false);
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

    public Member getByEmail(OAuthAttributes attributes) {
        return memberRepository.findMemberForLogin(attributes.getEmail())
                .orElse(attributes.toEntity());
    }
}



