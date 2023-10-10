package travelfeeldog.member.domain.application.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.member.domain.model.Member;
import travelfeeldog.member.dto.MemberDto;
import travelfeeldog.member.dto.MemberDtos.MemberResponse;
import travelfeeldog.member.dto.MemberNickNameHistoryDto;
@Transactional(readOnly = true)
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
