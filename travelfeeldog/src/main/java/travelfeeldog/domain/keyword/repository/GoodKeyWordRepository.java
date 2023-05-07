package travelfeeldog.domain.keyword.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import travelfeeldog.domain.keyword.model.GoodKeyWord;

public interface GoodKeyWordRepository extends JpaRepository<GoodKeyWord,Long> {
}
