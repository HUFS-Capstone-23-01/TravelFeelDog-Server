package travelfeeldog.community.feed.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import travelfeeldog.community.feed.domain.model.Feed;
import travelfeeldog.community.feed.domain.model.FeedImages;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
