package travelfeeldog.member.repository;

import java.util.concurrent.atomic.AtomicLong;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import travelfeeldog.member.domain.model.Member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    @PersistenceContext
    private final EntityManager em;
    // save only , save(save and update)
    @Override
    public Optional<Member> save(Member member) {
        try {
            Assert.notNull(member, "member must not be null");
            Member existingMember = null;
            if (member.getId() != null) { //could be change shorter
                existingMember = em.find(Member.class, member.getId());
            }
            if (existingMember == null) {
                em.persist(member);
            } else {
                em.merge(member);
            }
            return Optional.of(member);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Member> save(String nickName, String email, int level, int exp) {
        try {
            findByEmail(email).ifPresent(m -> {
                throw new IllegalStateException("가입되어 있습니다.");
            });
            Member member = Member.register(nickName, email, level, exp);
            em.persist(member);
            return Optional.of(member);
        } catch (IllegalStateException e) {
            return Optional.empty();
        }
    }
    @Override
    public Optional<Member> findByEmail(String email) {
        try {
            Member member = em.createQuery("SELECT m FROM Member m WHERE m.email = :email",
                            Member.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.of(member);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Member> findMemberForLogin(String email) {
        return findByEmail(email);
    }

    @Override
    public Optional<Member> findByNickName(String nickName) {
        try {
            Member member = em.createQuery("SELECT m FROM Member m WHERE m.nickName = :nickName",
                            Member.class)
                    .setParameter("nickName", nickName)
                    .getSingleResult();
            return Optional.of(member);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    @Override
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
    public Optional<Member> findById(AtomicLong atomicLong) {
        return findById(atomicLong.get());
    }
    @Override
    public void deleteMember(Member member) {
        em.remove(member);
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("SELECT m FROM Member m", Member.class)
                .getResultList();
    }

    @Override
    public List<Member> findAllByIdIn(List<Long> memberIds) {
        if (memberIds.isEmpty()) {
            return List.of();
        }
        return em.createQuery("SELECT m FROM Member m WHERE m.id IN :memberIds", Member.class)
                .setParameter("memberIds", memberIds)
                .getResultList();
    }


}