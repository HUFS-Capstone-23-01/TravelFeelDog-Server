package travelfeeldog.aggregate.member.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import travelfeeldog.aggregate.member.dto.MemberDtos;
import travelfeeldog.aggregate.member.dto.MemberDtos.MemberPostResponseDto;
import travelfeeldog.aggregate.member.dto.MemberDtos.MemberResponse;
import travelfeeldog.aggregate.member.dto.MemberDtos.MemberResponseExpDto;
import travelfeeldog.aggregate.member.domain.model.Member;
import travelfeeldog.aggregate.member.domain.application.MemberService;
import travelfeeldog.global.common.dto.ApiResponse;


import javax.validation.Valid;
import travelfeeldog.global.file.domain.application.ImageFileService;


@RestController
@RequestMapping(value = "/member")
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;
    private final ImageFileService imageFileService;

    @PostMapping(produces = "application/json;charset=UTF-8")
    public ApiResponse postMember(@Valid @RequestBody MemberDtos.MemberPostRequestDto request) throws Exception {
        Member savedMember = memberService.saveMember(request.getNickName(), request.getFirebaseToken());
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
            return ApiResponse.invaildToken(null);
        }
    }

    @DeleteMapping(produces = "application/json;charset=UTF-8")
    public ApiResponse deleteMemberByToken(@RequestHeader("Authorization") String firebaseToken) {
        if (memberService.isTokenExist(firebaseToken)) {
            memberService.deleteMember(firebaseToken);
            return ApiResponse.success("Delete Success");
        } else {
            return ApiResponse.invaildToken(false);
        }
    }

    @PutMapping(value = "/profile/image", produces = "application/json;charset=UTF-8")
    public ApiResponse putMemberImage(@RequestHeader("Authorization") String firebaseToken, @Valid @RequestParam("file") MultipartFile file) {
        if (memberService.isTokenExist(firebaseToken)) {
            String profileImageUrl = imageFileService.uploadImageFile(file, "member");
            Member result = memberService.updateImageUrl(firebaseToken, profileImageUrl);
            return ApiResponse.success(new MemberResponse(result));
        } else {
            return ApiResponse.invaildToken(false);
        }
    }

    @PutMapping(value = "/profile/nick", produces = "application/json;charset=UTF-8")
    public ApiResponse putMemberNickName(@Valid @RequestBody MemberDtos.MemberPutNickNameDto memberPutNickNameDto) {
        if (memberService.isTokenExist(memberPutNickNameDto.getFirebaseToken())) {
            Optional<Member> result = memberService
                    .updateNickName(memberPutNickNameDto.getFirebaseToken(), memberPutNickNameDto.getNickName());
            if(result.isEmpty()) {
                return ApiResponse.success(false);
            }
            else {
                return ApiResponse.success(new MemberResponse(result.get()));
            }
        } else {
            return ApiResponse.invaildToken(false);
        }
    }

    @PutMapping(value = "/profile/expAndLevel", produces = "application/json;charset=UTF-8")
    public ApiResponse putMemberExpAndLevel(@Valid @RequestBody MemberDtos.MemberPutExpDto memberPutExpDto) {
        if (memberService.isTokenExist(memberPutExpDto.getFirebaseToken())) {
            int addingValue = Integer.parseInt(memberPutExpDto.getAddingValue());
            Member result = memberService.updateExpAndLevel(memberPutExpDto.getFirebaseToken(), addingValue);
            return ApiResponse.success(new MemberResponse(result));
        } else {
            return ApiResponse.invaildToken(false);
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
            return ApiResponse.invaildToken(false);
        }
    }
}
