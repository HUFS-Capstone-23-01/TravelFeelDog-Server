package travelfeeldog.domain.member.application.usacase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travelfeeldog.domain.member.domain.service.MemberService;

@Service
@RequiredArgsConstructor
public class CreateFollowMemberUsacase {
    private final MemberService memberService;
    public void execute(){

    }
}
