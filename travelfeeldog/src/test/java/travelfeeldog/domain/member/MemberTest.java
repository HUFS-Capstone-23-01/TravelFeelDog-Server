package travelfeeldog.domain.member;

import org.joda.time.DateTimeFieldType;
import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import travelfeeldog.factory.MemberFixtureFactory;

class MemberTest {

    @DisplayName("업데이트 닉네임 변경 확인")
    @Test
    void testUpdateMemberNickName() {
        long seed = LocalDateTime.now().get(DateTimeFieldType.millisOfDay());
        var member = MemberFixtureFactory.create(seed);
        var expected = "cho";
        member.updateMemberNickName(expected);
        Assertions.assertEquals(expected, member.getNickName());
    }

    @DisplayName("닉네임 최대 길이 확인")
    @Test
    void testMemberNickNameMaxLength() {
        long seed = LocalDateTime.now().get(DateTimeFieldType.millisOfDay());
        var member = MemberFixtureFactory.create(seed);
        var overtLengthName = "12345678901";

        Assertions.assertThrows(
         IllegalArgumentException.class,
                ()->member.updateMemberNickName(overtLengthName)
        );
    }

}