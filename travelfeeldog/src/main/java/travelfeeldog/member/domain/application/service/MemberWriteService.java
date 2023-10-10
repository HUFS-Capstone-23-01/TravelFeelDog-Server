package travelfeeldog.member.domain.application.service;

import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.member.domain.model.Member;
import travelfeeldog.member.dto.MemberDtos.MemberPostRequestDto;
import travelfeeldog.member.dto.MemberDtos.MemberPostResponseDto;
@Transactional
public interface MemberWriteService {
    MemberPostResponseDto create(MemberPostRequestDto requestDto);

    void deleteMember(Member member);

    Member updateImageUrl(Member member, String imageUrl);

    Member updateNickName(Member member, String nickName);

    Member updateExpAndLevel(Member member, int addExpValue);
}
