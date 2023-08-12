package travelfeeldog.domain.member.infrastructure;

import java.util.List;
import java.util.Optional;
import travelfeeldog.domain.member.domain.model.Member;

public interface MemberRepository {

    Optional<Member> save(String nickName, String email, int i, int i1, String firebaseToken);
    Optional<Member> save(Member member);
    Optional<Member> findById(Long id);

    Optional<Member> findByNickName(String nickName);

    Optional<Member> findByToken(String firebaseToken);

    void deleteMember(Member member);

    List<Member> findAll();
}
