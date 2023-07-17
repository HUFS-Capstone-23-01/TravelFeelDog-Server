package travelfeeldog.aggregate.place.category.service;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.aggregate.place.category.dao.CategoryRepository;
import travelfeeldog.aggregate.place.category.dto.CategoryDtos.RequestCategoryDto;
import travelfeeldog.aggregate.place.category.model.Category;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.orElse(null);
    }

    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Category not found with Name: " + name));
    }
    @Transactional
    public Category saveCategory(RequestCategoryDto request) {
        Category category = new Category();
        category.setName(request.getName());
        return categoryRepository.save(category);
    }
    @Transactional
    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }
    @Transactional
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }
}
