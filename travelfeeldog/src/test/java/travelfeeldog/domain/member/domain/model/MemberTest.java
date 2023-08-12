package travelfeeldog.domain.member.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import travelfeeldog.domain.member.domain.util.MemberFixtureFactory;

class MemberTest {

    @DisplayName("업데이트 닉네임")
    @Test
    void updateMemberNickName() {
        var member = MemberFixtureFactory.create();
        var toChangeName = "cho";
        member.updateMemberNickName(toChangeName);
        Assertions.assertEquals(toChangeName, member.getNickName());
    }

}