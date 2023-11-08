package travelfeeldog.review.reviewpost.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import travelfeeldog.review.reviewpost.domain.model.ReviewImage;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
    Optional<ReviewImage> findByReviewId(Long reviewId);
}
