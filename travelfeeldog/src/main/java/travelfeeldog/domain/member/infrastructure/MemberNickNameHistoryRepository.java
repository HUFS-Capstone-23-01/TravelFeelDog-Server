package travelfeeldog.domain.member.infrastructure;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import travelfeeldog.domain.member.domain.model.MemberNickNameHistory;

public interface MemberNickNameHistoryRepository extends JpaRepository<MemberNickNameHistory,Long> {

    List<MemberNickNameHistory> findAllByMemberId(Long memberId);
}
