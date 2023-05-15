package travelfeeldog.domain.feed.feed.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import travelfeeldog.domain.feed.feed.model.FeedTag;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FeedTagRepository {
    @PersistenceContext
    private final EntityManager em;

    public Optional<FeedTag> findByFeedIdAndTagId(Long feedId, Long TagId) {
        try {
            FeedTag feedTag = em.createQuery("select f from FeedTag f " +
                            "left join fetch f.tag t " +
                            "where f.id = :fid and t.id = :tid", FeedTag.class)
                    .setParameter("tid", feedId)
                    .setParameter("fid", TagId)
                    .getSingleResult();
            return Optional.of(feedTag);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
    public List<FeedTag> findByTagContents(List<String> tags) {
        List<FeedTag> feedTags = em.createQuery("select f from FeedTag f " +
                        "left join fetch f.tag t " +
                        "where t.tagContent in (:tags)",FeedTag.class)
                .setParameter("tags", tags)
                .getResultList();

        return feedTags;
    }

}
