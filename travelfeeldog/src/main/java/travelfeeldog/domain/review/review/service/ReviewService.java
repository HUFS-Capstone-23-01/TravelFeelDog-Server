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
import travelfeeldog.domain.review.review.dao.ReviewRepository;
import travelfeeldog.domain.review.review.dto.ReviewDtos.ReviewMemberPageResponseDto;
import travelfeeldog.domain.review.review.dto.ReviewDtos.ReviewPageResponseDto;
import travelfeeldog.domain.review.review.dto.ReviewDtos.ReviewPostRequestDto;
import travelfeeldog.domain.review.review.model.RecommendStatus;
import travelfeeldog.domain.review.review.model.Review;
import travelfeeldog.domain.review.review.model.ReviewImage;
import travelfeeldog.domain.review.reviewkeyword.service.ReviewKeyWordService;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
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
    public ReviewPageResponseDto saveReview(ReviewPostRequestDto request, String token) {
        Member member = memberService.findByToken(token);
        member.updateExpAndLevel(20);
        Place place = placeService.getPlaceById(request.getPlaceId());
        place.updateReviewCount();
        placeService.addPlaceStatic(request);
        Review review = new Review(member, place, request.getAdditionalScript(), request.getRecommendStatus(),
                request.getSmallDogNumber(), request.getMediumDogNumber(), request.getLargeDogNumber());
        List<ReviewImage> reviewImages = request.getImageUrls()
                .stream()
                .map(imageUrl -> new ReviewImage(review, imageUrl))
                .collect(Collectors.toList());
        review.setReviewImages(reviewImages);
        reviewKeyWordService.saveReviewKeyWords(request.getBadKeyWordIds(),request.getGoodKeyWordIds(),review);
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

    public List<ReviewPageResponseDto> getReviewsByQuery(String request) {
        List<Review> reviews = "TIME".equalsIgnoreCase(request)
                ? reviewRepository.findAllByOrderByCreatedDateTimeDesc()
                : reviewRepository.findByRecommendStatus(RecommendStatus.valueOf(request.toUpperCase()));
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
}
