package travelfeeldog.aggregate.review.domain.keyword.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import travelfeeldog.aggregate.review.domain.keyword.model.GoodKeyWord;

public interface GoodKeyWordRepository extends JpaRepository<GoodKeyWord,Long> {
    List<GoodKeyWord> findAllByCategoryId(Long categoryId);
}
