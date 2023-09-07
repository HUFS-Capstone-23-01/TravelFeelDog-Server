package travelfeeldog.domain.member.infrastructure;

import java.util.concurrent.atomic.AtomicLong;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import travelfeeldog.domain.member.domain.model.Member;

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

    @Override
    public Optional<Member> save(Member member) {
        try {
            em.persist(member);
            return Optional.of(member);
        } catch (IllegalStateException e) {
            log.info("{}",e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Member> save(String nickName, String email, int level, int exp, String token) {
        try {
            findByToken(token).ifPresent(m -> {
                throw new IllegalStateException("가입되어 있습니다.");
            });
            Member member = Member.register(nickName, email, level, exp, token);
            em.persist(member);
            return Optional.of(member);
        } catch (IllegalStateException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Member> findByToken(String token) {
        try {
            Member member = em.createQuery("SELECT m FROM Member m WHERE m.token = :token",
                            Member.class)
                    .setParameter("token", token)
                    .getSingleResult();
            return Optional.of(member);
        } catch (NoResultException e) {
            return Optional.empty();
        }
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
