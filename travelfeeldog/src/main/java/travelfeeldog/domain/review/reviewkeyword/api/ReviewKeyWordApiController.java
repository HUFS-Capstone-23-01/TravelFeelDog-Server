package travelfeeldog.domain.review.reviewkeyword.api;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import travelfeeldog.domain.review.keyword.dto.KeyWordDtos.KeyWordResponseDto;
import travelfeeldog.domain.review.reviewkeyword.service.ReviewKeyWordService;
import travelfeeldog.global.common.dto.ApiResponse;
@RestController
@RequiredArgsConstructor
@RequestMapping("/review-keyword")
public class ReviewKeyWordApiController {

    private final ReviewKeyWordService reviewKeyWordService;

    @GetMapping(value ="/good/{placeId}",produces = "application/json;charset=UTF-8")
    public ApiResponse<List<KeyWordResponseDto>> getGoodKeyWordsByPlace(@PathVariable Long placeId) {
        List<KeyWordResponseDto> re =new ArrayList<>();
        return ApiResponse.success(re);
    }
    @GetMapping(value ="/bad/{placeId}",produces = "application/json;charset=UTF-8")
    public ApiResponse<List<KeyWordResponseDto>> getBadKeyWordsByPlace(@PathVariable Long placeId) {
        List<KeyWordResponseDto> re =new ArrayList<>();
        return ApiResponse.success(re);
    }
}
