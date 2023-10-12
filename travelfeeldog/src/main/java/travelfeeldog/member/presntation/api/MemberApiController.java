package travelfeeldog.member.presntation.api;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import travelfeeldog.global.auth.jwt.TokenResponse;
import travelfeeldog.member.domain.application.service.MemberReadWriteService;
import travelfeeldog.member.domain.application.service.MemberService;
import travelfeeldog.member.dto.MemberDtos.MemberRegisterResponse;
import travelfeeldog.member.dto.MemberNickNameHistoryDto;
import travelfeeldog.member.dto.MemberDtos;
import travelfeeldog.member.dto.MemberDtos.MemberPostResponseDto;
import travelfeeldog.member.dto.MemberDtos.MemberResponse;
import travelfeeldog.member.dto.MemberDtos.MemberResponseExpDto;
import travelfeeldog.member.domain.model.Member;
import travelfeeldog.global.common.dto.ApiResponse;


import jakarta.validation.Valid;
import travelfeeldog.global.file.domain.application.ImageFileService;


@RestController
@RequestMapping(value = "api/v1/member")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    private final MemberReadWriteService memberReadWriteService;
    private final ImageFileService imageFileService;

    @PostMapping(produces = "application/json;charset=UTF-8")
    public ApiResponse<MemberRegisterResponse> postMember(@Valid @RequestBody MemberDtos.MemberPostRequestDto request) {
        return ApiResponse.success(memberReadWriteService.register(request));
    }

    @GetMapping(value = "/total", produces = "application/json;charset=UTF-8")
    public ApiResponse<List<MemberResponse>> getAllMembers() {
        return ApiResponse.success(memberService.getAllMembers());
    }

    @GetMapping(produces = "application/json;charset=UTF-8")
    public ApiResponse<MemberResponse> getMemberByToken(@RequestHeader("Authorization") String firebaseToken) {
        Member member = memberService.findByToken(firebaseToken);
        return ApiResponse.success(new MemberResponse(member));
    }

    @DeleteMapping(produces = "application/json;charset=UTF-8")
    public ApiResponse deleteMemberByToken(@RequestHeader("Authorization") String firebaseToken) {
        Member member = memberService.findByToken(firebaseToken);
        memberService.deleteMember(member);
        return ApiResponse.success("Delete Success");
    }

    @PutMapping(value = "/profile/image", produces = "application/json;charset=UTF-8")
    public ApiResponse putMemberImage(@RequestHeader("Authorization") String firebaseToken, @Valid @RequestParam("file") MultipartFile file) {
        if (memberService.isTokenExist(firebaseToken)) {
            String profileImageUrl = imageFileService.uploadImageFile(file, "member");
            Member result = memberService.updateImageUrl(memberService.findByToken(firebaseToken), profileImageUrl);
            return ApiResponse.success(new MemberResponse(result));
        } else {
            return ApiResponse.invalidAccessToken(false);
        }
    }

    @PutMapping(value = "/profile/nick", produces = "application/json;charset=UTF-8")
    public ApiResponse putMemberNickName(@Valid @RequestBody MemberDtos.MemberPutNickNameDto memberPutNickNameDto) {
        if (memberService.isTokenExist(memberPutNickNameDto.getFirebaseToken())) {
            return ApiResponse.success(new MemberResponse(memberService
                    .updateNickName(memberService.findByToken(memberPutNickNameDto.getFirebaseToken()),
                        memberPutNickNameDto.getNickName())));
        } else {
            return ApiResponse.invalidAccessToken(false);
        }
    }

    @PutMapping(value = "/profile/expAndLevel", produces = "application/json;charset=UTF-8")
    public ApiResponse putMemberExpAndLevel(@Valid @RequestBody MemberDtos.MemberPutExpDto memberPutExpDto) {
        if (memberService.isTokenExist(memberPutExpDto.getFirebaseToken())) {
            int addingValue = Integer.parseInt(memberPutExpDto.getAddingValue());
            Member result = memberService.updateExpAndLevel(memberService.findByToken(memberPutExpDto.getFirebaseToken()), addingValue);
            return ApiResponse.success(new MemberResponse(result));
        } else {
            return ApiResponse.invalidAccessToken(false);
        }
    }

    @GetMapping(value = "/valid", produces = "application/json;charset=UTF-8")
    public ApiResponse checkMemberTokenValid(@RequestHeader("Authorization") String firebaseToken) {
        return ApiResponse.success(memberService.isTokenExist(firebaseToken));
    }

    @GetMapping(value = "/profile/nick/valid", produces = "application/json;charset=UTF-8")
    public ApiResponse checkNickRedundant(@RequestParam("nickName") String nickName) {
        return ApiResponse.success(memberService.isNickRedundant(nickName));
    }

    @GetMapping(value = "/findNick",produces = "application/json;charset=UTF-8")
    public ApiResponse getMemberByNick(@RequestParam("nickName") String nickName) {
        try {
            Member member = memberService.findByNickName(nickName);
            return ApiResponse.success(new MemberResponse(member));
        } catch (NoSuchElementException e) {
            return ApiResponse.success(false);
        }
    }

    @GetMapping(value = "/profile/exp", produces = "application/json;charset=UTF-8")
    public ApiResponse getMemberExp(@RequestHeader("Authorization") String firebaseToken) {
        try {
            Member member = memberService.findByToken(firebaseToken);
            return ApiResponse.success(new MemberResponseExpDto(member));
        } catch (NoSuchElementException e){
            return ApiResponse.invalidAccessToken(false);
        }
    }
    @GetMapping(value = "/{memberId}/history/total")
    public ApiResponse<List<MemberNickNameHistoryDto>> getMemberAllNickNameHistory(
                                                @PathVariable Long memberId,
                                                @RequestHeader("Authorization") String adminToken) {
        return ApiResponse.success(memberService.getAllMemberHistory(memberId));
    }
}
