package travelfeeldog.domain.member.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import travelfeeldog.domain.member.domain.model.Follow;
import travelfeeldog.domain.member.dto.MemberDto;
import travelfeeldog.domain.member.infrastructure.FollowRepository;

@RequiredArgsConstructor
@Service
public class FollowWriteService {

    final private FollowRepository followRepository;

    public Follow create(MemberDto fromMember, MemberDto toMember) {
        Assert.isTrue(!fromMember.id().equals(toMember.id()), "From, To 회원이 동일합니다.");
        var follow = Follow
                .builder()
                .fromMemberId(fromMember.id())
                .toMemberId(toMember.id())
                .build();
        return followRepository.save(follow);
    }
}
