package travelfeeldog.domain.review.service;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.domain.member.dao.MemberRepository;
import travelfeeldog.domain.member.model.Member;
import travelfeeldog.domain.member.service.MemberService;
import travelfeeldog.domain.place.dao.PlaceRepository;
import travelfeeldog.domain.place.dao.PlaceStaticRepository;
import travelfeeldog.domain.place.model.Place;
import travelfeeldog.domain.place.service.PlaceService;
import travelfeeldog.domain.review.dao.ReviewImageRepository;
import travelfeeldog.domain.review.dao.ReviewRepository;
import travelfeeldog.domain.review.dto.ReviewDtos.ReviewPostRequestDto;
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
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review getReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with id: " + id));
    }
    @Transactional
    public Review saveReview(ReviewPostRequestDto request, String token) {
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
        return review;
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

    public List<Review> getReviewsByPlaceId(Long placeId) {
        return reviewRepository.findByPlaceId(placeId);
    }

    public List<Review> getReviewsByMemberId(Long memberId) {
        return reviewRepository.findByMemberId(memberId);
    }
    public List<Review> getReviewsByDogSize(String dogSize) {
        if ("small".equals(dogSize)) {
            return reviewRepository.findBySmallDogNumberGreaterThan(0);
        } else if ("medium".equals(dogSize)) {
            return reviewRepository.findByMediumDogNumberGreaterThan(0);
        } else if ("large".equals(dogSize)) {
            return reviewRepository.findByLargeDogNumberGreaterThan(0);
        } else {
            throw new IllegalArgumentException("Invalid dog size: " + dogSize);
        }
    }


}
