package travelfeeldog.domain.follow.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import travelfeeldog.IntegrationTest;
import travelfeeldog.domain.member.domain.service.FollowWriteService;
import travelfeeldog.domain.member.dto.MemberDto;
import travelfeeldog.factory.MemberFixtureFactory;

@IntegrationTest
public class FollowWriteServiceTest {

    @Autowired
    private FollowWriteService followWriteService;

    @DisplayName("본인 계정을 팔로우 할 수 없다")
    @Test
    public void testSelfFollow() {
        var member = createMemberDto();

        assertThrows(
                IllegalArgumentException.class,
                () -> followWriteService.create(member, member)
        );
    }

    @DisplayName("팔로우 생성 테스트")
    @Test
    public void testCreate() {
        var fromMember = createMemberDto();
        var toMember = createMemberDto();

        var result = followWriteService.create(fromMember, toMember);

        assertNotNull(result.getId());
        assertEquals(fromMember.id(), result.getFromMemberId());
        assertEquals(toMember.id(), result.getToMemberId());
    }

    @DisplayName("fromMember, toMember 중복 팔로우 테스트")
    @Test
    public void testDuplicatedFollow() {
        var fromMember = createMemberDto();
        var toMember = fromMember;
        assertThrows(
                IllegalArgumentException.class,
                () -> followWriteService.create(fromMember, toMember)
        );
    }

    private MemberDto createMemberDto() {
        return MemberFixtureFactory.createDto();
    }
}
