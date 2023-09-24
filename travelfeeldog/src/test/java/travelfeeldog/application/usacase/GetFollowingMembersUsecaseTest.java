package travelfeeldog.application.usacase;

import org.joda.time.DateTimeFieldType;
import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opentest4j.TestAbortedException;
import org.springframework.beans.factory.annotation.Autowired;
import travelfeeldog.IntegrationTest;
import travelfeeldog.aggregate.member.domain.application.usecase.GetFollowingMembersUsecase;
import travelfeeldog.aggregate.member.domain.model.Follow;
import travelfeeldog.aggregate.member.domain.model.Member;
import travelfeeldog.aggregate.member.repository.FollowRepository;
import travelfeeldog.aggregate.member.repository.MemberRepository;
import travelfeeldog.factory.MemberFixtureFactory;

@IntegrationTest
public class GetFollowingMembersUsecaseTest {
    @Autowired
    private GetFollowingMembersUsecase usacase;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private FollowRepository followRepository;

    @DisplayName("팔로잉 회원 목록 조회")
    @Test
    public void testExecute() {
        var follow = Follow
                .builder()
                .fromMemberId(saveMember().getId())
                .toMemberId(saveMember().getId())
                .build();
        followRepository.save(follow);

        var result = usacase.execute(follow.getFromMemberId());
        System.out.println(result);
    }

    private Member saveMember() {
        long seed = LocalDateTime.now().get(DateTimeFieldType.millisOfDay());
        var member = MemberFixtureFactory.create(seed);
        return memberRepository.save(member).orElseThrow(() -> new TestAbortedException("[ERROR]"));
    }
}
