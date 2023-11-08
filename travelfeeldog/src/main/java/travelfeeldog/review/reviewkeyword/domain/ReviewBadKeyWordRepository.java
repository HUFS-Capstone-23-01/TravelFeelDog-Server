package travelfeeldog.review.reviewkeyword.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import travelfeeldog.review.reviewkeyword.domain.model.ReviewBadKeyWord;

@Repository
public interface ReviewBadKeyWordRepository extends JpaRepository<ReviewBadKeyWord, Long> {
    @Query("SELECT rbkw.badKeyWord.id FROM ReviewBadKeyWord rbkw WHERE rbkw.review.id = :reviewId")
    List<Long> getAllBadKeyWordIds(@Param("reviewId") Long reviewId);
}
