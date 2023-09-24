package travelfeeldog.aggregate.member.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import travelfeeldog.aggregate.member.domain.model.MemberNicknameHistory;

public interface MemberNicknameHistoryRepository extends JpaRepository<MemberNicknameHistory,Long> {

    List<MemberNicknameHistory> findAllByMemberId(Long memberId);

    List<MemberNicknameHistory> findAllByNickName(String nickName);


}
