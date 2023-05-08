package travelfeeldog.domain.keyword.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import travelfeeldog.domain.keyword.model.GoodKeyWord;

public interface GoodKeyWordRepository extends JpaRepository<GoodKeyWord,Long> {
    List<GoodKeyWord> findAllByCategoryId(Long categoryId);
}
