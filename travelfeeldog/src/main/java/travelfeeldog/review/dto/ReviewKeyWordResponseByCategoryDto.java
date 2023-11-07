package travelfeeldog.review.dto;

import java.util.List;
import lombok.Getter;
import travelfeeldog.review.domain.keyword.model.ReviewKeyWordInfo;

@Getter
public class ReviewKeyWordResponseByCategoryDto {
    private List<ReviewKeyWordInfo> keyWords;

    public ReviewKeyWordResponseByCategoryDto(List<ReviewKeyWordInfo> reviewKeyWordResponseDto) {
        this.keyWords = reviewKeyWordResponseDto;
    }
}
