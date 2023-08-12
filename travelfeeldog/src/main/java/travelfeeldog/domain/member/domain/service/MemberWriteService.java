package travelfeeldog.domain.member.domain.service;

import travelfeeldog.domain.member.domain.model.Member;
import travelfeeldog.domain.member.dto.MemberDtos.MemberPostRequestDto;

public interface MemberWriteService {
    Member save(MemberPostRequestDto requestDto);

    void deleteMember(String firebaseToken);

    Member updateImageUrl(String firebaseToken, String imageUrl);

    Member updateNickName(String firebaseToken, String nickName);

    Member updateExpAndLevel(String firebaseToken, int addExpValue);
}
