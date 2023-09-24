package travelfeeldog.aggregate.community.feed.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import travelfeeldog.aggregate.community.feed.domain.model.Feed;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FeedRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @PersistenceContext
    private final EntityManager em;
    public Feed save(Feed feed) {
        em.persist(feed);
        return feed;
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
    public void bulkInsert(List<Feed> feeds) {
        String sql = String.format(
                "INSERT INTO %s (member_id, feed_title, feed_body, created_time, updated_time) " +
                        "VALUES (:memberId, :title, :body, :createdTime, :updatedTime)",
                "feed");

        SqlParameterSource[] params = feeds.stream()
                //.map(BeanPropertySqlParameterSource::new) feed 에 member property 가 없어 사용 불가.
                .map(feed -> {
                    MapSqlParameterSource param = new MapSqlParameterSource();
                    param.addValue("memberId", feed.getMember().getId());
                    param.addValue("title", feed.getTitle());
                    param.addValue("body", feed.getBody());
                    param.addValue("createdTime", feed.getCreatedDateTime());
                    param.addValue("updatedTime", feed.getUpdatedDateTime());
                    return param;
                })
                .toArray(SqlParameterSource[]::new);

        namedParameterJdbcTemplate.batchUpdate(sql, params);
    }
}
