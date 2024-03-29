package travelfeeldog.review.reviewpost.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import travelfeeldog.review.reviewpost.domain.model.RecommendStatus;
import travelfeeldog.review.reviewpost.domain.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByPlaceId(Long placeId);

    List<Review> findByMemberId(Long memberId);

    List<Review> findByPlaceIdAndRecommendStatus(Long placeId, RecommendStatus recommendStatus);

    List<Review> findAllByPlaceIdOrderByCreatedDateTimeDesc(Long placeId);
}
