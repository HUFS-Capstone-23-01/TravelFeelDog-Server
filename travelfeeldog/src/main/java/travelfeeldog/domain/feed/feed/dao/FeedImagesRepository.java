package travelfeeldog.domain.feed.feed.dao;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import travelfeeldog.domain.feed.feed.model.Feed;
import travelfeeldog.domain.feed.feed.model.FeedImages;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FeedImagesRepository {
    @PersistenceContext
    private final EntityManager em;

    public List<FeedImages> findByUrls(List<String> urls) {
            try {
                List<FeedImages> feedImages = em.createQuery("select f from FeedImages f " +
                                "where f.feedImageUrl in :urls " +
                                "order by f.id desc", FeedImages.class)
                        .setParameter("urls", urls)
                        .getResultList();
                return feedImages;
            } catch (IllegalArgumentException e) {
                throw new IllegalStateException("urls : " + urls);
            }
    }

    public FeedImages save(Feed feed, String url) {
        //findByFeedAndUrl 필요
        FeedImages feedImages = FeedImages.create(feed, url);
        em.persist(feedImages);
        return feedImages;
    }
}
