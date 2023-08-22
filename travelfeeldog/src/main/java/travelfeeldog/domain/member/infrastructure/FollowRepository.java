package travelfeeldog.domain.member.infrastructure;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import travelfeeldog.domain.member.domain.model.Follow;

public interface FollowRepository extends JpaRepository<Follow,Long> {

    List<Follow> findAllByFromMemberId(Long memberId);

    List<Follow> findAllByToMemberId(Long memberId);
}
