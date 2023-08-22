package travelfeeldog.domain.member.application.usacase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travelfeeldog.domain.member.domain.service.FollowWriteService;
import travelfeeldog.domain.member.domain.service.MemberReadService;


@Service
@RequiredArgsConstructor
public class CreateFollowMemberUsecase {
    private final MemberReadService memberReadService;
    private final FollowWriteService followWriteService;
    public void execute(Long fromMemberId, Long toMemberId) {
        var fromMember = memberReadService.getMember(fromMemberId);
        var toMember = memberReadService.getMember(toMemberId);

        followWriteService.create(fromMember, toMember);
    }
}
