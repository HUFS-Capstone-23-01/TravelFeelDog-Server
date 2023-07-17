package travelfeeldog.aggregate.review.review.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import travelfeeldog.aggregate.review.review.model.ReviewImage;

public interface ReviewImageRepository extends JpaRepository<ReviewImage,Long> {
    Optional<ReviewImage> findByReviewId(Long reviewId);
}
