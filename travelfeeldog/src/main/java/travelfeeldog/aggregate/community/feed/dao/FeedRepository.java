package travelfeeldog.aggregate.community.feed.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import travelfeeldog.aggregate.member.domain.model.Member;
import travelfeeldog.aggregate.community.feed.model.Feed;
import travelfeeldog.aggregate.community.tag.model.Tag;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FeedRepository {
    @PersistenceContext
    private final EntityManager em;

    public Feed save(Member member, String title, String body) {
        Feed feed = Feed.create(member, 0, 0, title, body);
        em.persist(feed);
        return feed;
    }

    public Feed save(Member member,List<String> feedImages, String title, String body) {
        Feed feed = Feed.create(member, feedImages, 0, 0, title, body);
        em.persist(feed);
        return feed;
    }

    public Feed save(Member member, List<String> feedImages, String title, String body, List<Tag> feedTags) {
        Feed feed = Feed.create(member, feedImages, 0, 0, title, body, feedTags);
        em.persist(feed);
        return feed;
    }

    public Feed save(Member member, String title, String body, List<Tag> feedTags) {
        Feed feed = Feed.create(member, 0, 0, title, body, feedTags);
        em.persist(feed);
        return feed;
    }

    public void deleteById(Long id) {
        Feed feed = findById(id).orElseThrow(() -> new IllegalStateException("Memeber cannot delete this."));
        em.remove(feed);
    }

    public List<Feed> findListByNickName(String nickName, int offset) {
        List<Feed> feeds = em.createQuery("select f from Feed f " +
                        "where f.member.nickName = :nickName " +
                        "order by f.createdDateTime desc", Feed.class)
                .setParameter("nickName",nickName)
                .setFirstResult(offset)
                .setMaxResults(6)
                .getResultList();
        return feeds;
    }

    public Optional<Feed> findFeedDetail(Long id) {
        try {
            Feed feed = em.createQuery("select f from Feed f " +
                            "where f.id = :id", Feed.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.of(feed);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<Feed> findByTitle(String title) {
        List<Feed> result = em.createQuery("select f From Feed f " +
                        "where f.title like %:title% " +
                        "order by f.createdDateTime desc", Feed.class)
                .setParameter("title", title)
                .getResultList();
        return result;
    }


    public Optional<Feed> findById(Long id) {
        try {
            Feed feed = em.createQuery("select f from Feed f where f.id = :id", Feed.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.of(feed);
        } catch (IllegalStateException e) {
            return Optional.empty();
        }
    }

    public List<Feed> getListAll(int offset) {
        List<Feed> feeds = em.createQuery("select f from Feed f " +
                "order by f.createdDateTime desc", Feed.class)
                .setFirstResult(offset)
                .setMaxResults(6)
                .getResultList();
        return feeds;
    }
}
