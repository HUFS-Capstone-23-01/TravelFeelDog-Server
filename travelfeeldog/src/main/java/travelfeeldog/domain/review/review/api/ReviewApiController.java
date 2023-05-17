package travelfeeldog.domain.review.review.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import travelfeeldog.domain.review.review.dto.ReviewDtos.ReviewMemberPageResponseDto;
import travelfeeldog.domain.review.review.dto.ReviewDtos.ReviewPageResponseDto;
import travelfeeldog.domain.review.review.dto.ReviewDtos.ReviewPostRequestDto;
import travelfeeldog.domain.review.review.service.ReviewService;
import travelfeeldog.global.common.dto.ApiResponse;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewApiController {
    private final ReviewService reviewService;
    @GetMapping(value = "/test",produces = "application/json;charset=UTF-8")
    public ApiResponse<List<ReviewMemberPageResponseDto>> getAllReviews() {
        List<ReviewMemberPageResponseDto> reviews = reviewService.getAllReviews();
        return ApiResponse.success(reviews);
    }

    @GetMapping(value = "/{reviewId}",produces = "application/json;charset=UTF-8")
    public ApiResponse<ReviewMemberPageResponseDto> getReviewByReviewId(@PathVariable Long reviewId,@RequestHeader("Authorization") String token) {
        ReviewMemberPageResponseDto review = reviewService.getReviewById(reviewId);
        return ApiResponse.success(review);
    }

    @PostMapping(produces = "application/json;charset=UTF-8")
    public ApiResponse<ReviewPageResponseDto> saveReview(@RequestBody ReviewPostRequestDto request, @RequestHeader("Authorization") String token) {
        ReviewPageResponseDto savedReview = reviewService.saveReview(token,request);
        return ApiResponse.success(savedReview);
    }

    @DeleteMapping(value = "/{reviewId}",produces = "application/json;charset=UTF-8")
    public ApiResponse<Void> deleteReviewById(@PathVariable Long reviewId , @RequestHeader("Authorization") String token) {
        reviewService.deleteReviewById(reviewId);
        return ApiResponse.success(null);
    }

    @GetMapping(value = "/places/{placeId}",produces = "application/json;charset=UTF-8")
    public ApiResponse<List<ReviewPageResponseDto>> getReviewsByPlaceId(@PathVariable Long placeId,@RequestHeader("Authorization") String token) {
        List<ReviewPageResponseDto> reviews = reviewService.getReviewsByPlaceId(placeId);
        return ApiResponse.success(reviews);
    }
    @GetMapping(produces = "application/json;charset=UTF-8")
    public ApiResponse<List<ReviewPageResponseDto>> getReviewsByQuery(@RequestParam("request") String request, @RequestHeader("Authorization") String token) {
        List<ReviewPageResponseDto> reviews = reviewService.getReviewsByQuery(request);
        return ApiResponse.success(reviews);
    }

    @GetMapping(value = "/member/page",produces = "application/json;charset=UTF-8")
    public ApiResponse<List<ReviewMemberPageResponseDto>> getReviewsByMemberId(@RequestHeader("Authorization") String token) {
        List<ReviewMemberPageResponseDto> reviews = reviewService.getReviewsByMemberId(token);
        return ApiResponse.success(reviews);
    }


}
