package travelfeeldog.domain.member.domain.application;

import java.util.List;
import travelfeeldog.domain.member.domain.model.Member;

public interface MemberReadService {
    Member findByToken(String firebaseToken);

    Member findByNickName(String nickName);

    boolean isNickRedundant(String nickName);

    boolean isTokenExist(String firebaseToken);

    List<Member> getAll();
}
