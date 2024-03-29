package travelfeeldog.member.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travelfeeldog.member.application.service.MemberReadService;
import travelfeeldog.member.application.service.FollowWriteService;


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
