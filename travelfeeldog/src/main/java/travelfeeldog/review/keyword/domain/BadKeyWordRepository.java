package travelfeeldog.review.keyword.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import travelfeeldog.review.keyword.domain.model.BadKeyWord;

public interface BadKeyWordRepository extends JpaRepository<BadKeyWord, Long> {
    List<BadKeyWord> findAllByCategoryId(Long categoryId);
}
