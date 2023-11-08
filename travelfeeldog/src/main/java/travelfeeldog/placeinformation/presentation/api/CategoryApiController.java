package travelfeeldog.placeinformation.presentation.api;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import travelfeeldog.placeinformation.dto.CategoryDtos.CategoryResponseDto;
import travelfeeldog.placeinformation.dto.CategoryDtos.RequestCategoryDto;
import travelfeeldog.placeinformation.information.category.domain.model.Category;
import travelfeeldog.placeinformation.information.category.service.CategoryService;
import travelfeeldog.global.common.dto.ApiResponse;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryApiController {

    private final CategoryService categoryService;

    @GetMapping(value = "/all", produces = "application/json;charset=UTF-8")
    public ApiResponse<List<CategoryResponseDto>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ApiResponse.success(categories.stream().map(CategoryResponseDto::new).collect(Collectors.toList()));
    }

    @GetMapping(value = "/all/names", produces = "application/json;charset=UTF-8")
    public ApiResponse<List<String>> getAllCategoryNames() {
        List<Category> categories = categoryService.getAllCategories();
        return ApiResponse.success(categories.stream().map(Category::getName).collect(Collectors.toList()));
    }

    @PostMapping(consumes = "application/json", produces = "application/json;charset=UTF-8")
    public ApiResponse<Category> createCategory(@RequestBody RequestCategoryDto request) {
        Category createdCategory = categoryService.saveCategory(request);
        return ApiResponse.success(createdCategory);
    }

    @DeleteMapping(value = "/{categoryId}", produces = "application/json;charset=UTF-8")
    public ApiResponse<Void> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategoryById(categoryId);
        return ApiResponse.success(HttpStatus.NO_CONTENT);
    }
}
