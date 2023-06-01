package travelfeeldog.domain.member.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import travelfeeldog.domain.member.model.Member;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    @PersistenceContext
    private final EntityManager em;

    public Optional<Member> saveMember(String nickName, int level, int exp,String token) {
        try {
            findByToken(token).ifPresent(m -> {
                throw new IllegalStateException("가입되어 있습니다.");
            });
            Member member = Member.create(nickName, level, exp, token);
            em.persist(member);
            return Optional.of(member);
        } catch (IllegalStateException e) {
            return Optional.empty();
        }
    }

    public Optional<Member> findByToken(String token) {
        try {
            Member member = em.createQuery("SELECT m FROM Member m WHERE m.token = :token", Member.class)
                    .setParameter("token", token)
                    .getSingleResult();
            return Optional.of(member);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<Member> findByNickName(String nickName) {
        try {
            Member member = em.createQuery("SELECT m FROM Member m WHERE m.nickName = :nickName", Member.class)
                    .setParameter("nickName", nickName)
                    .getSingleResult();
            return Optional.of(member);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<Member> findById(Long id) {
        try {
            Member member = em.createQuery("SELECT m FROM Member m WHERE m.id = :id", Member.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.of(member);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public void deleteMember(Member member) {
        em.remove(member);
    }

    public Member updateMemberImageUrl(Member member, String newUrl) {
        member.setImageUrl(newUrl);
        em.merge(member);
        return member;
    }

    public Member updateExpAndLevel(Member member, int addingExp) {
        member.updateExpAndLevel(addingExp);
        em.merge(member);
        return member;
    }

    public List<Member> findAll() {
        return em.createQuery("SELECT m FROM Member m", Member.class)
                .getResultList();
    }
}
