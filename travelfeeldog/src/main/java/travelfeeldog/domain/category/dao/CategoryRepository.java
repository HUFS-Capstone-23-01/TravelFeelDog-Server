package travelfeeldog.domain.category.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import travelfeeldog.domain.category.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByName(String name);
}
