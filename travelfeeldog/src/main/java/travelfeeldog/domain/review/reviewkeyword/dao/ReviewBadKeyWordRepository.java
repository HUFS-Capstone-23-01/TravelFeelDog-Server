package travelfeeldog.domain.review.reviewkeyword.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import travelfeeldog.domain.review.reviewkeyword.model.ReviewBadKeyWord;

@Repository
public interface ReviewBadKeyWordRepository extends JpaRepository<ReviewBadKeyWord,Long> {
    List<Long> getAllBadKeyWordIds(Long reviewId);
}
