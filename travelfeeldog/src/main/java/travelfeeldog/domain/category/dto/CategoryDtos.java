package travelfeeldog.domain.category.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;
import travelfeeldog.domain.category.model.Category;
import travelfeeldog.domain.place.dto.PlaceDtos.PlaceDetailDto;

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
        private List<PlaceDetailDto> placeDetailDtos;
        public CategoryResponseDto(Category category){
            this.name = category.getName();
            this.placeDetailDtos = category.getPlaces()
                                            .stream()
                    .map(PlaceDetailDto::new).collect(Collectors.toList());
        }
    }

}
