package travelfeeldog.domain.feed.scrap.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import travelfeeldog.domain.feed.feed.model.Feed;
import travelfeeldog.domain.feed.scrap.model.Scrap;

public interface ScrapRepository extends JpaRepository<Scrap,Long> {
    List<Feed> findAllFeedByMemberId(Long memberId);
}
