package travelfeeldog.domain.member.infrastructure;

import java.util.List;
import java.util.Optional;
import travelfeeldog.domain.member.domain.model.Member;

public interface MemberRepository {

    Optional<Member> save(String nickName, String email, int level, int exp);
    Optional<Member> save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByNickName(String nickName);
    Optional<Member> findByEmail(String email);
    Optional<Member> findMemberForLogin(String email);
    void deleteMember(Member member);
    List<Member> findAll();
    List<Member> findAllByIdIn(List<Long> memberId);

}
