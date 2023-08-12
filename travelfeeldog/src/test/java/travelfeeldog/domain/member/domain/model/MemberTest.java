package travelfeeldog.domain.member.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import travelfeeldog.domain.member.domain.util.MemberFixtureFactory;

class MemberTest {

    @DisplayName("업데이트 닉네임 변경 확인")
    @Test
    void testUpdateMemberNickName() {
        var member = MemberFixtureFactory.create();
        var expected = "cho";
        member.updateMemberNickName(expected);
        Assertions.assertEquals(expected, member.getNickName());
    }

    @DisplayName("닉네임 최대 길이 확인")
    @Test
    void testMemberNickNameMaxLength() {
        var member = MemberFixtureFactory.create();
        var overtLengthName = "12345678901";

        Assertions.assertThrows(
         IllegalArgumentException.class,
                ()->member.updateMemberNickName(overtLengthName)
        );
    }

}