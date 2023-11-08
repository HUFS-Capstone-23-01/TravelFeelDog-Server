package travelfeeldog.member.presntation.api;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import travelfeeldog.global.common.dto.ApiResponse;
import travelfeeldog.global.file.domain.application.ImageFileService;
import travelfeeldog.member.application.service.MemberReadWriteService;
import travelfeeldog.member.application.service.MemberService;
import travelfeeldog.member.domain.model.Member;
import travelfeeldog.member.dto.MemberDtos;
import travelfeeldog.member.dto.MemberDtos.MemberRegisterResponse;
import travelfeeldog.member.dto.MemberDtos.MemberResponse;


@RestController
@RequestMapping(value = "api/v1/member")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    private final MemberReadWriteService memberReadWriteService;
    private final ImageFileService imageFileService;

    @PostMapping(value = "/register", produces = "application/json;charset=UTF-8")
    public ApiResponse<MemberRegisterResponse> postMember(@Valid @RequestBody MemberDtos.MemberPostRequestDto request) {
        return ApiResponse.success(memberReadWriteService.register(request));
    }

    @GetMapping(value = "/register/nick/duplicate", produces = "application/json;charset=UTF-8")
    public ApiResponse<Boolean> checkNickRedundant(@RequestParam("nickName") String nickName) {
        return ApiResponse.success(memberService.isNickRedundant(nickName));
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
    public ApiResponse<MemberResponse> putMemberImage(@RequestHeader("Authorization") String firebaseToken,
                                                      @Valid @RequestParam("file") MultipartFile file) {
        String profileImageUrl = imageFileService.uploadImageFile(file, "member");
        Member result = memberService.updateImageUrl(memberService.findByToken(firebaseToken), profileImageUrl);
        return ApiResponse.success(new MemberResponse(result));
    }

    @PutMapping(value = "/profile/nick", produces = "application/json;charset=UTF-8")
    public ApiResponse<MemberResponse> putMemberNickName(
            @Valid @RequestBody MemberDtos.MemberPutNickNameDto memberPutNickNameDto) {
        return ApiResponse.success(new MemberResponse(
                memberService.updateNickName(memberService.findByToken(memberPutNickNameDto.getFirebaseToken()),
                        memberPutNickNameDto.getNickName())));
    }

    @PutMapping(value = "/profile/expAndLevel", produces = "application/json;charset=UTF-8")
    public ApiResponse<MemberResponse> putMemberExpAndLevel(
            @Valid @RequestBody MemberDtos.MemberPutExpDto memberPutExpDto) {
        int addingValue = Integer.parseInt(memberPutExpDto.getAddingValue());
        Member result = memberService.updateExpAndLevel(memberService.findByToken(memberPutExpDto.getFirebaseToken()),
                addingValue);
        return ApiResponse.success(new MemberResponse(result));
    }
}
