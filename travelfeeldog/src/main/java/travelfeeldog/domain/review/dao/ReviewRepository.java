package travelfeeldog.domain.review.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import travelfeeldog.domain.review.model.RecommendStatus;
import travelfeeldog.domain.review.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findByPlaceId(Long placeId);

    List<Review> findByMember_memberId(Long memberId);
    List<Review> findByRecommendStatus(RecommendStatus recommendStatus);
    List<Review> findAllByOrderByCreatedDateTimeDesc();
    List<Review> findBySmallDogNumberGreaterThan(int i);
    List<Review> findByMediumDogNumberGreaterThan(int i);
    List<Review> findByLargeDogNumberGreaterThan(int i);
}
