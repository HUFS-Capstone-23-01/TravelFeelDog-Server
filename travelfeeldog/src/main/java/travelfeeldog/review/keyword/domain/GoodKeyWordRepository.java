package travelfeeldog.review.keyword.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import travelfeeldog.review.keyword.domain.model.GoodKeyWord;

public interface GoodKeyWordRepository extends JpaRepository<GoodKeyWord, Long> {
    List<GoodKeyWord> findAllByCategoryId(Long categoryId);
}
