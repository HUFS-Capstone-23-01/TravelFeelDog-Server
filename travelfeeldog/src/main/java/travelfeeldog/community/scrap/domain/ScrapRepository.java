package travelfeeldog.community.scrap.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import travelfeeldog.community.scrap.domain.model.Scrap;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    List<Scrap> findAllByMemberId(Long memberId);

    @Query("SELECT DISTINCT s " +
            "FROM Scrap s " +
            "WHERE s.member.id = :memberId AND s.feed.id = :feedId")
    List<Scrap> findScrapsByMemberIdAndFeedId(Long memberId, Long feedId);

}
