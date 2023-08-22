package travelfeeldog.domain.member.application.usacase;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import travelfeeldog.domain.member.domain.model.Follow;
import travelfeeldog.domain.member.domain.service.FollowReadService;
import travelfeeldog.domain.member.domain.service.MemberReadService;
import travelfeeldog.domain.member.dto.MemberDto;

@RequiredArgsConstructor
@Service
public class GetFollowingMembersUsecase {
    private final MemberReadService memberReadService;
    private final FollowReadService followReadService;

    public List<MemberDto> execute(Long memberId) {
        var followings = followReadService.getFollowings(memberId);
        var followingMemberIds = followings.stream().map(Follow::getToMemberId).toList();
        return memberReadService.getMembers(followingMemberIds);
    }
}
