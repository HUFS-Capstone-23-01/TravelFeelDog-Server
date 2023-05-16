package travelfeeldog.domain.feed.scrap.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import travelfeeldog.domain.feed.scrap.dto.ScrapDtos.ScrapRequestDto;
import travelfeeldog.domain.feed.scrap.service.ScrapService;
import travelfeeldog.global.common.dto.ApiResponse;

@RestController
@RequestMapping("/scrap")
@RequiredArgsConstructor
public class ScrapApiController {
    private final ScrapService scrapService;

    @PostMapping(produces = "application/json;charset=UTF-8")
    public ApiResponse postScrap(@RequestHeader("Authorization") String token,@RequestBody ScrapRequestDto requestDto) {
        return ApiResponse.success(scrapService.addNewScrap(token, requestDto));
    }
    @GetMapping(value = "/all",produces = "application/json;charset=UTF-8")
    public ApiResponse postScrap(@RequestHeader("Authorization") String token) {
        return ApiResponse.success(scrapService.getAllMemberScrap(token));
    }


}