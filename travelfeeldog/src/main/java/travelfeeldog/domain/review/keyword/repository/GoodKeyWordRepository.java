package travelfeeldog.domain.review.keyword.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import travelfeeldog.domain.review.keyword.model.GoodKeyWord;

public interface GoodKeyWordRepository extends JpaRepository<GoodKeyWord,Long> {
    List<GoodKeyWord> findAllByCategoryId(Long categoryId);
}
