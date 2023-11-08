package travelfeeldog.review.domain.review.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.member.application.service.MemberService;
import travelfeeldog.member.domain.model.Member;
import travelfeeldog.place.domain.place.model.Place;
import travelfeeldog.place.domain.place.service.PlaceService;
import travelfeeldog.review.domain.review.model.RecommendStatus;
import travelfeeldog.review.domain.review.model.Review;
import travelfeeldog.review.domain.review.model.ReviewImage;
import travelfeeldog.review.domain.review.repositoy.ReviewImageRepository;
import travelfeeldog.review.domain.review.repositoy.ReviewRepository;
import travelfeeldog.review.domain.reviewkeyword.service.ReviewKeyWordService;
import travelfeeldog.review.dto.ReviewDtos.ReviewMemberPageResponseDto;
import travelfeeldog.review.dto.ReviewDtos.ReviewPageResponseDto;
import travelfeeldog.review.dto.ReviewDtos.ReviewPostRequestDto;
import travelfeeldog.review.dto.ReviewDtos.UpdateReviewImageDto;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final PlaceService placeService;
    private final MemberService memberService;
    private final ReviewKeyWordService reviewKeyWordService;

    @Transactional
    public ReviewPageResponseDto saveReview(String token, ReviewPostRequestDto request) {
        Member member = memberService.findByToken(token);
        Place place = placeService.getPlaceById(request);
        Review review = Review.AddReview(member, place, request);

        member.updateExpAndLevel(20);
        place.updatePlaceStatistic(request);

        reviewKeyWordService.saveReviewKeyWords(request, review);

        return new ReviewPageResponseDto(review);
    }

    @Transactional
    public String updateReviewImage(UpdateReviewImageDto reviewImageDto) {
        ReviewImage reviewImage = reviewImageRepository.findByReviewId(reviewImageDto.reviewId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Review not found with id: " + reviewImageDto.reviewId()));
        reviewImage.updateImage(reviewImageDto.imageUrl());
        return reviewImageDto.imageUrl();
    }

    @Transactional
    public void deleteReviewById(Long id) {
        reviewRepository.deleteById(id);
    }

    public List<ReviewMemberPageResponseDto> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(ReviewMemberPageResponseDto::new)
                .toList();
    }

    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with id: " + reviewId));
    }

    public ReviewMemberPageResponseDto getReviewMemberPageByReviewId(Long reviewId) {
        Review review = getReviewById(reviewId);
        return new ReviewMemberPageResponseDto(review);
    }

    public List<ReviewPageResponseDto> getReviewsByQuery(Long placeId, String request) {
        List<Review> reviews = "TIME".equalsIgnoreCase(request)
                ? reviewRepository.findAllByPlaceIdOrderByCreatedDateTimeDesc(placeId)
                : reviewRepository.findByPlaceIdAndRecommendStatus(placeId,
                        RecommendStatus.valueOf(request.toUpperCase()));
        return reviews
                .stream()
                .map(ReviewPageResponseDto::new)
                .toList();
    }

    public List<ReviewMemberPageResponseDto> getReviewsByMemberId(String token) {
        Long memberId = memberService.findByToken(token).getId();
        return reviewRepository.findByMemberId(memberId)
                .stream()
                .map(ReviewMemberPageResponseDto::new)
                .toList();
    }
}
