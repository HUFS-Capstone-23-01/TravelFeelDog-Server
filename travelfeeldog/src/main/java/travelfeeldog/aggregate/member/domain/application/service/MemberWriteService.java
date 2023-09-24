package travelfeeldog.aggregate.member.domain.application.service;

import travelfeeldog.aggregate.member.domain.model.Member;
import travelfeeldog.aggregate.member.dto.MemberDtos.MemberPostRequestDto;
import travelfeeldog.aggregate.member.dto.MemberDtos.MemberPostResponseDto;

public interface MemberWriteService {
    MemberPostResponseDto create(MemberPostRequestDto requestDto);

    void deleteMember(Member member);

    Member updateImageUrl(Member member, String imageUrl);

    Member updateNickName(Member member, String nickName);

    Member updateExpAndLevel(Member member, int addExpValue);
}
