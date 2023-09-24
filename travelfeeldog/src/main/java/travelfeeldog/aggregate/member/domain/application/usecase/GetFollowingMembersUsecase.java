package travelfeeldog.aggregate.member.domain.application.usecase;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import travelfeeldog.aggregate.member.domain.application.service.MemberReadService;
import travelfeeldog.aggregate.member.dto.MemberDto;
import travelfeeldog.aggregate.member.domain.model.Follow;
import travelfeeldog.aggregate.member.domain.application.service.FollowReadService;

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
