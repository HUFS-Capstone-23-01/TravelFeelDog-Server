package travelfeeldog.review.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import travelfeeldog.global.common.dto.ApiResponse;
import travelfeeldog.review.domain.reviewkeyword.service.ReviewKeyWordService;
import travelfeeldog.review.dto.ReviewKeyWordResponseByCategoryDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review-keyword")
public class ReviewKeyWordApiController {

    private final ReviewKeyWordService reviewKeyWordService;

    @GetMapping(value = "/{placeId}", produces = "application/json;charset=UTF-8")
    public ApiResponse<ReviewKeyWordResponseByCategoryDto> getGoodOrBadKeyWordsByPlace(@PathVariable Long placeId,
                                                                                       @RequestParam("keyWord") String keyWord,
                                                                                       @RequestHeader("Authorization") String token) {
        return ApiResponse.success(reviewKeyWordService.getGoodOrBadKeyWordsByPlace(placeId, keyWord));
    }
}
