package travelfeeldog.domain.member;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import travelfeeldog.IntegrationTest;
import travelfeeldog.domain.member.domain.service.MemberRead;
import travelfeeldog.domain.member.infrastructure.MemberRepository;
import travelfeeldog.factory.MemberFixtureFactory;

@IntegrationTest
public class MemberReadServiceTest {

    @Autowired
    private MemberRead service;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원 조회 테스트")
    @Test
    public void testGetMember() {
        var member = MemberFixtureFactory.create();
        var id = memberRepository.save(member).get().getId();

        var result = service.getMember(id);

        Assertions.assertEquals(id, result.id());
    }


    @DisplayName("회원 조회 실패")
    @Test
    public void testNotFound() {
        Assertions.assertThrows(
                NoSuchElementException.class,
                () -> service.getMember(-1L)
        );
    }
}
