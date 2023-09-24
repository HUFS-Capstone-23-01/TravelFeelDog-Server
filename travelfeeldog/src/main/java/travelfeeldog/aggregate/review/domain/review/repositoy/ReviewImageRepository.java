package travelfeeldog.aggregate.review.domain.review.repositoy;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import travelfeeldog.aggregate.review.domain.review.model.ReviewImage;

public interface ReviewImageRepository extends JpaRepository<ReviewImage,Long> {
    Optional<ReviewImage> findByReviewId(Long reviewId);
}
