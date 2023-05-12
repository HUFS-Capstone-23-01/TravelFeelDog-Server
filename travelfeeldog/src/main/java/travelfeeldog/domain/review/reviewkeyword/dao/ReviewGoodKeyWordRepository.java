package travelfeeldog.domain.review.reviewkeyword.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import travelfeeldog.domain.review.reviewkeyword.model.ReviewGoodKeyWord;
@Repository
public interface ReviewGoodKeyWordRepository extends JpaRepository<ReviewGoodKeyWord,Long> {
}
