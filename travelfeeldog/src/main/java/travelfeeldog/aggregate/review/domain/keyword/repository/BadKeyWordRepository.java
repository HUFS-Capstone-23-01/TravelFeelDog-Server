package travelfeeldog.aggregate.review.domain.keyword.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import travelfeeldog.aggregate.review.domain.keyword.model.BadKeyWord;

public interface BadKeyWordRepository extends JpaRepository<BadKeyWord,Long> {
    List<BadKeyWord> findAllByCategoryId(Long categoryId);
}
