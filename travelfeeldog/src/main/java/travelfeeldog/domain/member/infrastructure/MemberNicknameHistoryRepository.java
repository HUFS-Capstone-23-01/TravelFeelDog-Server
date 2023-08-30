package travelfeeldog.domain.member.infrastructure;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import travelfeeldog.domain.member.domain.model.MemberNicknameHistory;

public interface MemberNicknameHistoryRepository extends JpaRepository<MemberNicknameHistory,Long> {

    List<MemberNicknameHistory> findAllByMemberId(Long memberId);

    List<MemberNicknameHistory> findAllByNickName(String nickName);


}
