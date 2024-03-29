package travelfeeldog.placeinformation.information.category.service;

import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.placeinformation.information.category.domain.CategoryRepository;
import travelfeeldog.placeinformation.information.category.domain.model.Category;
import travelfeeldog.placeinformation.dto.CategoryDtos.RequestCategoryDto;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with Id: " + id));
    }

    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with Name: " + name));
    }

    @Transactional
    public Category saveCategory(RequestCategoryDto request) {
        Category category = new Category(request.getName());
        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }

    @Transactional
    public void deleteCategoryById(Long id) {
        Category category = getCategoryById(id);
        deleteCategory(category);
    }
}
