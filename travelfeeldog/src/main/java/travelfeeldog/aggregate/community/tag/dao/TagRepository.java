package travelfeeldog.aggregate.community.tag.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import travelfeeldog.aggregate.community.tag.model.Tag;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TagRepository {
    @PersistenceContext
    private final EntityManager em;

    public boolean isExistTagContent(String tagContent) {
        return findByTagContent(tagContent).isPresent();
    }

    public Optional<Tag> findByTagContent(String tagContent) {
        try {
            Tag result = em.createQuery("select t from Tag t where t.tagContent = :tagContent", Tag.class)
                    .setParameter("tagContent", tagContent)
                    .getSingleResult();
            return Optional.of(result);
        } catch(NoResultException e) {
            return Optional.empty();
        }
    }

    public Tag save(String tagContent) {
        Tag tag = Tag.create(tagContent);
        em.persist(tag);
        return tag;
    }
}
