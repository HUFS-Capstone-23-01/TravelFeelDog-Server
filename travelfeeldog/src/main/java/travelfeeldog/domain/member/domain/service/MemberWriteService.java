package travelfeeldog.domain.member.domain.service;

import travelfeeldog.domain.member.domain.model.Member;
import travelfeeldog.domain.member.dto.MemberDtos.MemberPostRequestDto;
import travelfeeldog.domain.member.dto.MemberDtos.MemberPostResponseDto;

public interface MemberWriteService {
    MemberPostResponseDto create(MemberPostRequestDto requestDto);

    void deleteMember(Member member);

    Member updateImageUrl(Member member, String imageUrl);

    Member updateNickName(Member member, String nickName);

    Member updateExpAndLevel(Member member, int addExpValue);
}
