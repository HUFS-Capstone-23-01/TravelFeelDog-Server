package travelfeeldog.aggregate.member.domain.application.service;

import java.util.List;
import travelfeeldog.aggregate.member.domain.model.Member;
import travelfeeldog.aggregate.member.dto.MemberDto;
import travelfeeldog.aggregate.member.dto.MemberDtos.MemberResponse;
import travelfeeldog.aggregate.member.dto.MemberNickNameHistoryDto;

public interface MemberReadService {
    Member  findByToken(String firebaseToken);

    Member findByNickName(String nickName);

    boolean isNickRedundant(String nickName);

    boolean isTokenExist(String firebaseToken);

    List<MemberResponse> getAllMembers() ;

    List<MemberNickNameHistoryDto> getAllMemberHistory(Long memberId);

    MemberDto getMember(Long fromMemberId);

    List<MemberDto> getMembers(List<Long> followingMemberIds);
}
