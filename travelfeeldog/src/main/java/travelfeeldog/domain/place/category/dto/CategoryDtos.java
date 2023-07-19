package travelfeeldog.domain.place.category.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;
import travelfeeldog.domain.place.category.model.Category;
import travelfeeldog.domain.place.place.dto.PlaceDtos.PlaceDetailDto;

public class CategoryDtos {
    @Data
    @NoArgsConstructor
    public static class RequestCategoryDto{
        private String name;
    }
    @Data
    @NoArgsConstructor
    public static class CategoryResponseDto{
        private String name;
        private List<PlaceDetailDto> placeDetails;
        public CategoryResponseDto(Category category){
            this.name = category.getName();
            this.placeDetails = category.getPlaceDetails();
        }
    }

}
