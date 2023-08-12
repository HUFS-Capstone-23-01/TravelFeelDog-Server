package travelfeeldog.domain.member.domain.service;

import java.util.List;
import travelfeeldog.domain.member.domain.model.Member;
import travelfeeldog.domain.member.dto.MemberNickNameHistoryDto;

public interface MemberReadService {
    Member findByToken(String firebaseToken);

    Member findByNickName(String nickName);

    boolean isNickRedundant(String nickName);

    boolean isTokenExist(String firebaseToken);

    List<Member> getAll();

    List<MemberNickNameHistoryDto> getAllMemberHistory(Long memberId);
}
