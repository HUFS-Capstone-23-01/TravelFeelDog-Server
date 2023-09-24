package travelfeeldog.aggregate.place.dto;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import travelfeeldog.aggregate.place.domain.category.model.Category;
import travelfeeldog.aggregate.place.dto.PlaceDtos.PlaceDetailDto;

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
