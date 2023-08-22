package travelfeeldog.domain.member.domain.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import travelfeeldog.domain.member.domain.model.Member;
import travelfeeldog.domain.member.dto.MemberDto;
import travelfeeldog.domain.member.dto.MemberDtos.MemberPostRequestDto;
import travelfeeldog.domain.member.dto.MemberNickNameHistoryDto;

@Service
@RequiredArgsConstructor
public class MemberReadWriteService implements MemberService {

    @Qualifier("memberReadService")
    private final MemberReadService memberReadService;

    @Qualifier("memberWrite")
    private final MemberWriteService memberWriteService;

    @Override
    public Member findByToken(String firebaseToken) {
        return memberReadService.findByToken(firebaseToken);
    }
    @Override
    public MemberDto getMember(Long memberId) {
        return memberReadService.getMember(memberId);
    }
    @Override
    public List<MemberDto> getMembers(List<Long> followingMemberIds){
        return memberReadService.getMembers(followingMemberIds);
    }

    @Override
    public Member findByNickName(String nickName) {
        return memberReadService.findByNickName(
                nickName); // corrected from findByToken to findByNickName
    }

    @Override
    public boolean isNickRedundant(String nickName) {
        return memberReadService.isNickRedundant(nickName);
    }

    @Override
    public boolean isTokenExist(String firebaseToken) {
        return memberReadService.isTokenExist(firebaseToken);
    }

    @Override
    public List<Member> getAll() {
        return memberReadService.getAll();
    }

    @Override
    public List<MemberNickNameHistoryDto> getAllMemberHistory(Long memberId) {
        return memberReadService.getAllMemberHistory(memberId);
    }

    // MemberWriteService
    @Override
    public Member save(MemberPostRequestDto requestDto) {
        return memberWriteService.save(requestDto);
    }

    @Override
    public void deleteMember(String firebaseToken) {
        memberWriteService.deleteMember(firebaseToken);
    }

    @Override
    public Member updateImageUrl(String firebaseToken, String imageUrl) {
        return memberWriteService.updateImageUrl(firebaseToken, imageUrl);
    }

    @Override
    public Member updateNickName(String firebaseToken, String nickName) {
        return memberWriteService.updateNickName(firebaseToken, nickName);
    }

    @Override
    public Member updateExpAndLevel(String firebaseToken, int addExpValue) {
        return memberWriteService.updateExpAndLevel(firebaseToken, addExpValue);
    }
}
