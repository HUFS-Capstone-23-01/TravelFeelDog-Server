package travelfeeldog.domain.member.application.controller;

import java.util.List;
import java.util.NoSuchElementException;

import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import travelfeeldog.domain.member.domain.service.MemberReadService;
import travelfeeldog.domain.member.dto.MemberDtos;
import travelfeeldog.domain.member.dto.MemberDtos.MemberPostResponseDto;
import travelfeeldog.domain.member.dto.MemberDtos.MemberResponse;
import travelfeeldog.domain.member.dto.MemberDtos.MemberResponseExpDto;
import travelfeeldog.domain.member.domain.model.Member;
import travelfeeldog.domain.member.domain.service.MemberService;
import travelfeeldog.domain.member.dto.MemberNickNameHistoryDto;
import travelfeeldog.global.common.dto.ApiResponse;


import javax.validation.Valid;
import travelfeeldog.global.file.domain.application.ImageFileService;


@RestController
@RequestMapping(value = "/member")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    private final MemberReadService memberReadService;
    private final ImageFileService imageFileService;

    @PostMapping(produces = "application/json;charset=UTF-8")
    public ApiResponse postMember(@Valid @RequestBody MemberDtos.MemberPostRequestDto request) throws Exception {
        Member savedMember = memberService.save(request);
        return ApiResponse.success(new MemberPostResponseDto(savedMember));
    }

    @GetMapping(value = "/total", produces = "application/json;charset=UTF-8")
    public ApiResponse getAllMembers() {
        List<Member> findMembers = memberService.getAll();
        List<MemberResponse> responseList = findMembers.stream().map(MemberResponse::new).collect(Collectors.toList());
        return ApiResponse.success(responseList);
    }

    @GetMapping(produces = "application/json;charset=UTF-8")
    public ApiResponse getMemberByToken(@RequestHeader("Authorization") String firebaseToken) {
        if (memberService.isTokenExist(firebaseToken)) {
            Member member = memberService.findByToken(firebaseToken);
            return ApiResponse.success(new MemberResponse(member));
        } else {
            return ApiResponse.invalidToken(null);
        }
    }

    @DeleteMapping(produces = "application/json;charset=UTF-8")
    public ApiResponse deleteMemberByToken(@RequestHeader("Authorization") String firebaseToken) {
        if (memberService.isTokenExist(firebaseToken)) {
            memberService.deleteMember(firebaseToken);
            return ApiResponse.success("Delete Success");
        } else {
            return ApiResponse.invalidToken(false);
        }
    }

    @PutMapping(value = "/profile/image", produces = "application/json;charset=UTF-8")
    public ApiResponse putMemberImage(@RequestHeader("Authorization") String firebaseToken, @Valid @RequestParam("file") MultipartFile file) {
        if (memberService.isTokenExist(firebaseToken)) {
            String profileImageUrl = imageFileService.uploadImageFile(file, "member");
            Member result = memberService.updateImageUrl(firebaseToken, profileImageUrl);
            return ApiResponse.success(new MemberResponse(result));
        } else {
            return ApiResponse.invalidToken(false);
        }
    }

    @PutMapping(value = "/profile/nick", produces = "application/json;charset=UTF-8")
    public ApiResponse putMemberNickName(@Valid @RequestBody MemberDtos.MemberPutNickNameDto memberPutNickNameDto) {
        if (memberService.isTokenExist(memberPutNickNameDto.getFirebaseToken())) {
            return ApiResponse.success(new MemberResponse(memberService
                    .updateNickName(memberPutNickNameDto.getFirebaseToken(),
                        memberPutNickNameDto.getNickName())));
        } else {
            return ApiResponse.invalidToken(false);
        }
    }

    @PutMapping(value = "/profile/expAndLevel", produces = "application/json;charset=UTF-8")
    public ApiResponse putMemberExpAndLevel(@Valid @RequestBody MemberDtos.MemberPutExpDto memberPutExpDto) {
        if (memberService.isTokenExist(memberPutExpDto.getFirebaseToken())) {
            int addingValue = Integer.parseInt(memberPutExpDto.getAddingValue());
            Member result = memberService.updateExpAndLevel(memberPutExpDto.getFirebaseToken(), addingValue);
            return ApiResponse.success(new MemberResponse(result));
        } else {
            return ApiResponse.invalidToken(false);
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
    public ApiResponse GetMemberByNick(@RequestParam("nickName") String nickName) {
        try {
            Member member = memberService.findByNickName(nickName);
            return ApiResponse.success(new MemberResponse(member));
        } catch (NoSuchElementException e) {
            return ApiResponse.success(false);
        }
    }

    @GetMapping(value = "/profile/getexp", produces = "application/json;charset=UTF-8")
    public ApiResponse GetMemberExp(@RequestHeader("Authorization") String firebaseToken) {
        try {
            Member member = memberService.findByToken(firebaseToken);
            return ApiResponse.success(new MemberResponseExpDto(member));
        } catch (NoSuchElementException e){
            return ApiResponse.invalidToken(false);
        }
    }
    @GetMapping(value = "/{memberId}/history/total")
    public ApiResponse<List<MemberNickNameHistoryDto>> getMemberAllNickNameHistory(
                                                @PathVariable Long memberId,
                                                @RequestHeader("Authorization") String adminToken) {
        return ApiResponse.success(memberReadService.getAllMemberHistory(memberId));
    }
}
