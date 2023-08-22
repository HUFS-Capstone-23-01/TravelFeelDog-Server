package travelfeeldog.domain.community.feedlike.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import travelfeeldog.domain.community.feedlike.model.FeedLike;

import java.util.List;

public interface FeedLikeRepository extends JpaRepository<FeedLike,Long> {
    List<FeedLike> findAllByMemberId(Long memberId);
    @Query("SELECT DISTINCT f " +
            "FROM FeedLike f " +
            "WHERE f.member.id = :memberId AND f.feed.id = :feedId")
    List<FeedLike> findFeedLikesByMemberIdAndFeedId(Long memberId, Long feedId);

}
