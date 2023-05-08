package travelfeeldog.domain.review.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import travelfeeldog.domain.review.model.ReviewImage;

public interface ReviewImageRepository extends JpaRepository<ReviewImage,Long> {
}
