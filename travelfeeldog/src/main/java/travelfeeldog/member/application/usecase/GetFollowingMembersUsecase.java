package travelfeeldog.member.application.usecase;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import travelfeeldog.member.application.service.MemberReadService;
import travelfeeldog.member.dto.MemberDto;
import travelfeeldog.member.domain.model.Follow;
import travelfeeldog.member.application.service.FollowReadService;

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
