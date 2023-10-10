package travelfeeldog.review.domain.review.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import travelfeeldog.member.domain.model.Member;
import travelfeeldog.member.domain.application.service.MemberService;
import travelfeeldog.place.domain.place.model.Place;
import travelfeeldog.place.domain.place.service.PlaceService;
import travelfeeldog.review.domain.review.repositoy.ReviewImageRepository;
import travelfeeldog.review.domain.review.repositoy.ReviewRepository;
import travelfeeldog.review.dto.ReviewDtos.ReviewMemberPageResponseDto;
import travelfeeldog.review.dto.ReviewDtos.ReviewPageResponseDto;
import travelfeeldog.review.dto.ReviewDtos.ReviewPostRequestDto;
import travelfeeldog.review.dto.ReviewDtos.UpdateReviewImageDto;
import travelfeeldog.review.domain.review.model.RecommendStatus;
import travelfeeldog.review.domain.review.model.Review;
import travelfeeldog.review.domain.review.model.ReviewImage;
import travelfeeldog.review.domain.reviewkeyword.service.ReviewKeyWordService;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final PlaceService placeService;
    private final MemberService memberService;
    private final ReviewKeyWordService reviewKeyWordService;

    public List<ReviewMemberPageResponseDto> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(ReviewMemberPageResponseDto::new)
                .collect(Collectors.toList());
    }

    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with id: " + reviewId));
    }
    public ReviewMemberPageResponseDto getReviewMemberPageByReviewId(Long reviewId){
        Review review = getReviewById(reviewId);
        return new ReviewMemberPageResponseDto(review);
    }

    @Transactional
    public ReviewPageResponseDto saveReview(String token , ReviewPostRequestDto request) {
        Member member = memberService.findByToken(token);
        Place place = placeService.getPlaceById(request);
        Review review = Review.AddReview(member, place, request);

        member.updateExpAndLevel(20);
        place.updatePlaceStatistic(request);

        reviewKeyWordService.saveReviewKeyWords(request,review);

        return new ReviewPageResponseDto(review);
    }
    @Transactional
    public void deleteReviewById(Long id) {
        reviewRepository.deleteById(id);
    }

    public List<ReviewPageResponseDto> getReviewsByPlaceId(Long placeId) {
        return reviewRepository.findByPlaceId(placeId)
                .stream()
                .map(ReviewPageResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<ReviewPageResponseDto> getReviewsByQuery(String token ,Long placeId,String request) {
        Member member = memberService.findByToken(token);
        List<Review> reviews = "TIME".equalsIgnoreCase(request)
                ? reviewRepository.findAllByPlaceIdOrderByCreatedDateTimeDesc(placeId)
                : reviewRepository.findByPlaceIdAndRecommendStatus(placeId, RecommendStatus.valueOf(request.toUpperCase()));
        return reviews
                .stream()
                .map(ReviewPageResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<ReviewMemberPageResponseDto> getReviewsByMemberId(String token) {
        Long memberId = memberService.findByToken(token).getId();
        return reviewRepository.findByMemberId(memberId)
                .stream()
                .map(ReviewMemberPageResponseDto::new)
                .collect(Collectors.toList());
    }
    @Transactional
    public String updateReviewImage(String token, UpdateReviewImageDto reviewImageDto) {
        memberService.findByToken(token);
        ReviewImage reviewImage = reviewImageRepository.findByReviewId(reviewImageDto.getReviewId())
            .orElseThrow(() -> new EntityNotFoundException("Review not found with id: " +reviewImageDto.getReviewId()));;
        reviewImage.updateImage(reviewImageDto.getImageUrl());
        return reviewImageDto.getImageUrl();
    }
}