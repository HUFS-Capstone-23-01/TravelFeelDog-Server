package travelfeeldog.domain.member.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import travelfeeldog.domain.member.dto.MemberDto;
import travelfeeldog.domain.member.application.usacase.CreateFollowMemberUsecase;
import travelfeeldog.domain.member.application.usacase.GetFollowingMembersUsecase;

@Tag(name = "팔로우 정보")
@RequiredArgsConstructor
@RestController
@RequestMapping("/follow")
public class FollowApiController {
    final private CreateFollowMemberUsecase createFollowMemberUsecase;
    final private GetFollowingMembersUsecase getFollowingMembersUsecase;

    @Operation(summary = "팔로우 등록")
    @PostMapping("/{fromId}/{toId}")
    public List<MemberDto> register(@PathVariable Long fromId, @PathVariable Long toId) {
        createFollowMemberUsecase.execute(fromId, toId);
        return getFollowingMembersUsecase.execute(fromId);
    }

    @Operation(summary = "팔로워 조회")
    @GetMapping("/members/{fromId}")
    public List<MemberDto> getFollowers(@PathVariable Long fromId) {
        return getFollowingMembersUsecase.execute(fromId);
    }
}