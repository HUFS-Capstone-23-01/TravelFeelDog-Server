package travelfeeldog.domain.category.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

public class CategoryDtos {
    @Data
    @NoArgsConstructor
    public static class RequestCategoryDto{
        private String name;
    }
}
