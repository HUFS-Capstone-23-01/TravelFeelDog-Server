package travelfeeldog.domain.community.feed.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import travelfeeldog.domain.community.feed.model.Feed;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FeedTagRepository {
    @PersistenceContext
    private final EntityManager em;

    public List<Feed> findByTagContents(List<String> tagContents, int offset) {
        List<Feed> feeds = em.createQuery("select t.feed from FeedTag t " +
                        "where t.tag.tagContent in :tags " +
                        "order by t.feed.createdDateTime",Feed.class)
                .setParameter("tags", tagContents)
                .setFirstResult(offset)
                .setMaxResults(6)
                .getResultList();

        return feeds;
    }
}
