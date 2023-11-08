package travelfeeldog.community.presntation.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import travelfeeldog.community.dto.FeedLikeDtos.FeedLikeRequestDto;
import travelfeeldog.community.dto.FeedLikeDtos.FeedLikesByMemberResponseDto;
import travelfeeldog.community.feedlike.service.FeedLikeService;
import travelfeeldog.global.common.dto.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/feedLike")
@RequiredArgsConstructor
public class FeedLikeApiController {
    private final FeedLikeService feedLikeService;

    @PostMapping(produces = "application/json;charset=UTF-8")
    public ApiResponse<Boolean> postScrap(@RequestHeader("Authorization") String token,
                                          @RequestBody FeedLikeRequestDto requestDto) {
        return ApiResponse.success(feedLikeService.addNewScrap(token, requestDto));
    }

    @GetMapping(value = "/all", produces = "application/json;charset=UTF-8")
    public ApiResponse<List<FeedLikesByMemberResponseDto>> getFeedLikesByMember(
            @RequestHeader("Authorization") String token) {
        return ApiResponse.success(feedLikeService.getAllMemberFeedLike(token));
    }

    @DeleteMapping(produces = "application/json;charset=UTF-8")
    public ApiResponse<Boolean> deleteFeedLike(@RequestHeader("Authorization") String token,
                                               @RequestParam("feedLikeId") Long feedLikeId) {
        return ApiResponse.success(feedLikeService.deleteFeedLike(token, feedLikeId));
    }
}
