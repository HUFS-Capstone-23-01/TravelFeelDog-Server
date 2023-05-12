package travelfeeldog.domain.review.review.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import travelfeeldog.domain.review.review.model.ReviewImage;

public interface ReviewImageRepository extends JpaRepository<ReviewImage,Long> {
}
