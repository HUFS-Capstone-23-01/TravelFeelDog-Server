package travelfeeldog.domain.member;

import java.time.LocalDateTime;
import org.joda.time.DateTimeFieldType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import travelfeeldog.IntegrationTest;
import travelfeeldog.domain.member.domain.model.Member;
import travelfeeldog.domain.member.domain.model.MemberNicknameHistory;
import travelfeeldog.domain.member.domain.service.MemberWriteService;
import travelfeeldog.domain.member.dto.MemberDtos.MemberPostRequestDto;
import travelfeeldog.domain.member.dto.MemberDtos.MemberPostResponseDto;
import travelfeeldog.domain.member.infrastructure.MemberNicknameHistoryRepository;
import travelfeeldog.domain.member.infrastructure.MemberRepository;
import org.junit.jupiter.api.Assertions;

import travelfeeldog.factory.MemberFixtureFactory;

@IntegrationTest
public class MemberWriteServiceTest {
    @Qualifier("memberWrite")
    @Autowired
    private MemberWriteService service;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberNicknameHistoryRepository memberNicknameHistoryRepository;

    @DisplayName("회원정보 등록 테스트")
    @Test
    public void testRegister() {
        var command = new MemberPostRequestDto("cho12", "pnu@fastcampus.com", "thisIsToken"+ LocalDateTime.now());

        var member = service.create(command);

        assertEqualsCreateMember(command, member);
    }

    @DisplayName("회원정보 이름 변경 테스트")
    @Test
    public void testChangeName() {
        Member saved = saveMember();
        var expected = "chair";

        service.updateNickName(saved, expected);

        var result = memberRepository.findById(saved.getId()).orElseThrow();
        Assertions.assertEquals(expected, result.getNickName());
    }

    @DisplayName("회원정보 이름 변경시 변경 이름의 히스토리가 저장된다")
    @Test
    public void testToSaveHistoryWhenChangeName() {
        Member member = saveMember();
        var nameToChange = "chair";

        service.updateNickName(member, nameToChange);
        var results = memberNicknameHistoryRepository.findAllByNickName(nameToChange);

        Assertions.assertEquals(1, results.size());
        MemberNicknameHistory memberNicknameHistory = results.get(0);
        Assertions.assertEquals(nameToChange, memberNicknameHistory.getNickName());
    }


    private Member saveMember() {
        long seed = org.joda.time.LocalDateTime.now().get(DateTimeFieldType.millisOfDay());
        var member = MemberFixtureFactory.create(seed);
        return memberRepository.save(member).orElseThrow();
    }

    private void assertEqualsCreateMember(MemberPostRequestDto command, MemberPostResponseDto reponse) {
        Assertions.assertNotNull(reponse.getId());
        Assertions.assertEquals(command.getNickName(), reponse.getNickName());
        Assertions.assertEquals(command.getFirebaseToken(), reponse.getToken());
    }

}
