package travelfeeldog.domain.review.keyword.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import travelfeeldog.domain.review.keyword.model.BadKeyWord;

public interface BadKeyWordRepository extends JpaRepository<BadKeyWord,Long> {
    List<BadKeyWord> findAllByCategoryId(Long categoryId);
}
