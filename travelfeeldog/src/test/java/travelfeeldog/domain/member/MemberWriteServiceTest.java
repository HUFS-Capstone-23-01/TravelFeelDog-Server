package travelfeeldog.domain.member;

import java.util.HashMap;
import java.util.Map;
import org.joda.time.DateTimeFieldType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import travelfeeldog.IntegrationTest;
import travelfeeldog.global.auth.jwt.response.TokenResponse;

import travelfeeldog.infra.oauth2.dto.OAuthAttributes;
import travelfeeldog.member.domain.model.Member;
import travelfeeldog.member.domain.model.MemberNicknameHistory;
import travelfeeldog.member.application.service.MemberWriteService;
import travelfeeldog.member.dto.MemberDtos.MemberPostRequestDto;
import travelfeeldog.member.dto.MemberDtos.MemberPostResponseDto;
import travelfeeldog.member.domain.MemberNicknameHistoryRepository;
import travelfeeldog.member.domain.MemberRepository;
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
        String userEmail = "newOne@gmail.com";
        String userNickName = "cho12";
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("name", userNickName);
        attributes.put("email", userEmail);

        OAuthAttributes authAttributes = OAuthAttributes.of("gogole", userNickName, attributes);
        Member member = authAttributes.toEntity();
        memberRepository.save(member);
        var command = new MemberPostRequestDto("cho12", userEmail);

        // Register after google login auth complete
        var tokenResponse = new TokenResponse("accTk", "rctk");
        member = memberRepository.save(command.getEmail(), tokenResponse.accessToken(), tokenResponse.refreshToken());
        var result = new MemberPostResponseDto(member);
        assertEqualsCreateMember(command, result);
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
    }

}
