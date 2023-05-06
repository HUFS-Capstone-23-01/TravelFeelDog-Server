package travelfeeldog.domain.member.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import travelfeeldog.domain.member.dto.MemberDtos.MemberPostRequestDto;
import travelfeeldog.domain.member.dto.MemberDtos.MemberResponse;
import travelfeeldog.domain.member.model.Member;
import travelfeeldog.domain.member.service.MemberService;
import travelfeeldog.global.common.dto.ApiResponse;


@RestController
@RequestMapping(value = "/member")
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping(produces = "application/json;charset=UTF-8")
    public ApiResponse postMember(@RequestBody MemberPostRequestDto request) throws Exception {
        Member savedMember = memberService.saveMember(request.getNickName(), request.getImageUrl(), request.getToken());
        return ApiResponse.success(new MemberResponse(savedMember));
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
            return ApiResponse.invaildToken(false);
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
    public ApiResponse putMemberImage(@RequestHeader("Authorization") String firebaseToken, @RequestParam("file") MultipartFile file) {
        if (memberService.isTokenExist(firebaseToken)) {
            String imageUrl = fileService.uplodaFile(file).getFileUrl();
            Member result = memberService.updateImageUrl(firebaseToken, imageUrl);
            return ApiResponse.success(result);
        } else {
            return ApiResponse.invaildToken(false);
        }
    }

    @PutMapping(value = "/profile/nick", produces = "application/json;charset=UTF-8")
    public ApiResponse putMemberNickName(@RequestHeader("Authorization") String firebaseToken, @RequestParam("nickname") String newNickName) {
        if (memberService.isTokenExist(firebaseToken)) {
            Member result = memberService.updateNickName(firebaseToken, newNickName);
            return ApiResponse.success(result);
        } else {
            return ApiResponse.invaildToken(false);
        }
    }

    @PutMapping(value = "/profile/expAndLevel", produces = "application/json;charset=UTF-8")
    public ApiResponse putMemberExpAndLevel(@RequestHeader("Authorization") String firebaseToken, @RequestParam("expValue") int expForAdd) {
        if (memberService.isTokenExist(firebaseToken)) {
            Member result = memberService.updateExpAndLevel(firebaseToken, expForAdd);
            return ApiResponse.success(result);
        } else {
            return ApiResponse.invaildToken(false);
        }
    }

    @PostMapping(value = "/valid", produces = "application/json;charset=UTF-8")
    public ApiResponse checkMemberTokenValid(@RequestHeader("Authorization") String firebaseToken) {
        return ApiResponse.invaildToken(memberService.isTokenExist(firebaseToken));
    }

    @PostMapping(value = "/profile/nick/valid", produces = "application/json;charset=UTF-8")
    public ApiResponse checkNickRedundant(@RequestHeader("Authorization") String newNickName) {
        return ApiResponse.invaildToken(memberService.isNickRedundant(newNickName));
    }
}