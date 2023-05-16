package travelfeeldog.domain.feed.scrap.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import travelfeeldog.domain.feed.feed.model.Feed;
import travelfeeldog.domain.feed.scrap.model.Scrap;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap,Long> {
    boolean isNew(Scrap scrap);
    List<Feed> findAllFeedByMemberId(Long memberId);
}
