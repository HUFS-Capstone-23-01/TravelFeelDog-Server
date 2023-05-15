package travelfeeldog.domain.review.reviewkeyword.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import travelfeeldog.domain.review.reviewkeyword.model.ReviewGoodKeyWord;
@Repository
public interface ReviewGoodKeyWordRepository extends JpaRepository<ReviewGoodKeyWord,Long> {
    @Query("SELECT rgkw.goodKeyWord.id FROM ReviewGoodKeyWord rgkw WHERE rgkw.review.id = :reviewId")
    List<Long> getAllGoodKeyWordIds(@Param("reviewId") Long reviewId);
}
