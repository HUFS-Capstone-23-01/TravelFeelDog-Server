package travelfeeldog.domain.category.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.domain.category.dao.CategoryRepository;
import travelfeeldog.domain.category.dto.CategoryDtos.RequestCategoryDto;
import travelfeeldog.domain.category.model.Category;

@Transactional(readOnly = true)
@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.orElse(null);
    }

    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
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