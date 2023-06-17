package travelfeeldog.domain.community.scrap.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import travelfeeldog.domain.community.scrap.dto.ScrapDtos.ScrapByMemberResponseDto;
import travelfeeldog.domain.community.scrap.dto.ScrapDtos.ScrapRequestDto;
import travelfeeldog.domain.community.scrap.service.ScrapService;
import travelfeeldog.global.common.dto.ApiResponse;

@RestController
@RequestMapping("/scrap")
@RequiredArgsConstructor
public class ScrapApiController {
    private final ScrapService scrapService;

    @PostMapping(produces = "application/json;charset=UTF-8")
    public ApiResponse<Boolean> postScrap(@RequestHeader("Authorization") String token,@RequestBody ScrapRequestDto requestDto) {
        return ApiResponse.success(scrapService.addNewScrap(token, requestDto));
    }
    @GetMapping(value = "/all",produces = "application/json;charset=UTF-8")
    public ApiResponse<List<ScrapByMemberResponseDto>> getScrapsByMember(@RequestHeader("Authorization") String token) {
        return ApiResponse.success(scrapService.getAllMemberScrap(token));
    }
    @DeleteMapping(produces = "application/json;charset=UTF-8")
    public ApiResponse<Boolean> deleteScrap(@RequestHeader("Authorization") String token, @RequestParam("scrapId") Long scrapId) {
        return ApiResponse.success(scrapService.deleteScrap(token,scrapId));
    }
}
