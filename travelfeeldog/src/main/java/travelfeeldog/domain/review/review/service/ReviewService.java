package travelfeeldog.domain.review.review.service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import travelfeeldog.domain.member.model.Member;
import travelfeeldog.domain.member.service.MemberService;
import travelfeeldog.domain.place.place.model.Place;
import travelfeeldog.domain.place.place.service.PlaceService;
import travelfeeldog.domain.review.review.dao.ReviewImageRepository;
import travelfeeldog.domain.review.review.dao.ReviewRepository;
import travelfeeldog.domain.review.review.dto.ReviewDtos.ReviewMemberPageResponseDto;
import travelfeeldog.domain.review.review.dto.ReviewDtos.ReviewPageResponseDto;
import travelfeeldog.domain.review.review.dto.ReviewDtos.ReviewPostRequestDto;
import travelfeeldog.domain.review.review.dto.ReviewDtos.UpdateReviewImageDto;
import travelfeeldog.domain.review.review.model.RecommendStatus;
import travelfeeldog.domain.review.review.model.Review;
import travelfeeldog.domain.review.review.model.ReviewImage;
import travelfeeldog.domain.review.reviewkeyword.service.ReviewKeyWordService;

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

    public ReviewMemberPageResponseDto getReviewById(Long reviewId) {
        Review review= reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with id: " + reviewId));
        return new ReviewMemberPageResponseDto(review);
    }

    @Transactional
    public ReviewPageResponseDto saveReview(String token ,ReviewPostRequestDto request) {
        Member member = memberService.findByToken(token);
        Place place = placeService.getPlaceById(request);
        Review review = new Review(member, place, request);

        member.updateExpAndLevel(20);
        placeService.addPlaceStatic(request);
        reviewKeyWordService.saveReviewKeyWords(request,review);

        reviewRepository.save(review);
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
                : reviewRepository.findByPlaceIdAndRecommendStatus(placeId,RecommendStatus.valueOf(request.toUpperCase()));
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
        Long reviewId = reviewImageDto.getReviewId() ;
        ReviewImage reviewImage = reviewImageRepository.findByReviewId(reviewId)
            .orElseThrow(() -> new EntityNotFoundException("Review not found with id: " + reviewId));;
        reviewImage.updateImage(reviewImageDto.getImageUrl());
        return reviewImageDto.getImageUrl();
    }
}
