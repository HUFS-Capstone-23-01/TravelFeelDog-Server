package travelfeeldog.domain.keyword.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import travelfeeldog.domain.keyword.model.BadKeyWord;

public interface BadKeyWordRepository extends JpaRepository<BadKeyWord,Long> {
}
