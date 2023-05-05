package travelfeeldog.domain.member.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Optional<Member> saveMember(Long id, String nickName, int level, int exp, String imageUrl, String token) {
        //DB에 회원 정보 저장
        try {
            findByToken(token).ifPresent( m -> {
                throw new IllegalStateException("가입되어 있습니다.");
            });
            Member member = Member.create(id, nickName, level, exp, imageUrl, token);
            em.persist(member);
            return Optional.of(member);
        } catch (IllegalStateException e) {
            return Optional.empty();
        }
    }
    public Optional<Member> findByToken(String token) {//토큰 검색
        try {
            Member member = em.createQuery("select m from Member m where m.token = :token", Member.class).setParameter("token", token).getSingleResult();
            return Optional.of(member);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    public Optional<Member> findByNickName(String nickName) { //닉네임 검색
        try {
            Member member = em.createQuery("select m from Member m where m.nickName = :nickName", Member.class).setParameter("nickName", nickName).getSingleResult();
            return Optional.of(member);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    public void deleteMember(String inputToken) {//회원탈퇴
        Member member = findByToken(inputToken).get();
        em.remove(member);
    }
    public Member updateMemberImageUrl(String memberToken, String newUrl) {
        //회원사진 업데이트
        Member member = findByToken(memberToken).get();
        member.setImageUrl(newUrl);
        em.merge(member);
        return member;
    }
    public Member updateNickName(String memberToken, String newNick) {
        //회원닉네임 수정(중복 검사 포함)
        Member member = findByToken(memberToken).get();
        findByNickName(newNick).ifPresent( m -> {
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");}
        );
        member.setNickName(newNick);
        em.merge(member);
        return member;
    }
    public List<Member> findAll() { //전체 조회
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }


}
