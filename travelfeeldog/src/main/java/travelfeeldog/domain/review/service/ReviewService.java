package travelfeeldog.domain.review.service;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.util.EnumUtils;
import travelfeeldog.domain.member.model.Member;
import travelfeeldog.domain.member.service.MemberService;
import travelfeeldog.domain.place.model.Place;
import travelfeeldog.domain.place.service.PlaceService;
import travelfeeldog.domain.review.dao.ReviewImageRepository;
import travelfeeldog.domain.review.dao.ReviewRepository;
import travelfeeldog.domain.review.dto.ReviewDtos.ReviewMemberPageResponseDto;
import travelfeeldog.domain.review.dto.ReviewDtos.ReviewPageResponseDto;
import travelfeeldog.domain.review.dto.ReviewDtos.ReviewPostRequestDto;
import travelfeeldog.domain.review.model.RecommendStatus;
import travelfeeldog.domain.review.model.Review;
import travelfeeldog.domain.review.model.ReviewImage;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final PlaceService placeService;
    private final ReviewImageRepository reviewImageRepository;
    private final MemberService memberService;
    public List<ReviewMemberPageResponseDto> getAllReviews() {
        return reviewRepository.findAll().stream().map(ReviewMemberPageResponseDto::new).collect(Collectors.toList());
    }

    public ReviewMemberPageResponseDto getReviewById(Long reviewId) {
        Review review= reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with id: " + reviewId));
        return new ReviewMemberPageResponseDto(review);
    }
    @Transactional
    public ReviewPageResponseDto saveReview(ReviewPostRequestDto request, String token) {
        Review review = new Review();
        Member member = memberService.findByToken(token);
        Place place = placeService.getPlaceById(request.getPlaceId());
        review.setPlace(place);
        review.setMember(member);
        review.setAdditionalScript(request.getAdditionalScript());
        review.setRecommendStatus(request.getRecommendStatus());
        review.setSmallDogNumber(request.getSmallDogNumber());
        review.setMediumDogNumber(request.getMediumDogNumber());
        review.setLargeDogNumber(request.getLargeDogNumber());
        reviewRepository.save(review);
        saveReviewImage(request.getImageUrls(),review);
        placeService.addPlaceStatic(request);
        return new ReviewPageResponseDto(review);
    }
    @Transactional
    public void saveReviewImage(List<String> imageUrls ,Review review){
        if(imageUrls.size() >0) {
            for(String imageUrl : imageUrls){
                ReviewImage reviewImage = new ReviewImage();
                reviewImage.setReview(review);
                reviewImage.setImageUrl(imageUrl);
                reviewImageRepository.save(reviewImage);
            }
        }
    }
    @Transactional
    public void deleteReviewById(Long id) {
        reviewRepository.deleteById(id);
    }

    public List<ReviewPageResponseDto> getReviewsByPlaceId(Long placeId) {
        return reviewRepository.findByPlaceId(placeId).stream()
                .map(ReviewPageResponseDto::new)
                .collect(Collectors.toList());
    }
    public List<ReviewPageResponseDto> getReviewsByQuery(String request) {
        List<Review> reviews = "TIME".equalsIgnoreCase(request)
                ? reviewRepository.findAllByOrderByCreatedDateTimeDesc()
                : reviewRepository.findByRecommendStatus(RecommendStatus.valueOf(request.toUpperCase()));
        return reviews.stream().map(ReviewPageResponseDto::new).collect(Collectors.toList());
    }

    public List<ReviewMemberPageResponseDto> getReviewsByMemberId(String token) {
        Long memberId = memberService.findByToken(token).getId();
        return reviewRepository.findByMemberId(memberId).stream()
                .map(ReviewMemberPageResponseDto::new)
                .collect(Collectors.toList());
    }

}
