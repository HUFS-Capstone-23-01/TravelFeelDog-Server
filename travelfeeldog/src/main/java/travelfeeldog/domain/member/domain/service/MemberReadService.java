package travelfeeldog.domain.member.domain.service;

import java.util.List;
import travelfeeldog.domain.member.domain.model.Member;
import travelfeeldog.domain.member.dto.MemberDto;
import travelfeeldog.domain.member.dto.MemberDtos.MemberResponse;
import travelfeeldog.domain.member.dto.MemberNickNameHistoryDto;

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
