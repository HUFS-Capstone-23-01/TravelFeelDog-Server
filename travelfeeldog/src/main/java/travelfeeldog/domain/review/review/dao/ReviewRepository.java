package travelfeeldog.domain.review.review.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import travelfeeldog.domain.review.review.model.RecommendStatus;
import travelfeeldog.domain.review.review.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findByPlaceId(Long placeId);

    List<Review> findByMemberId(Long memberId);
    List<Review> findByPlaceIdAndRecommendStatus(Long placeId, RecommendStatus recommendStatus);
    List<Review> findAllByPlaceIdOrderByCreatedDateTimeDesc(Long placeId);
}
