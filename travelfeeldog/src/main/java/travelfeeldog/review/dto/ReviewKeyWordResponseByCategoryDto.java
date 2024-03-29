package travelfeeldog.review.dto;

import java.util.List;
import lombok.Getter;
import travelfeeldog.review.keyword.domain.model.ReviewKeyWordInfo;

@Getter
public class ReviewKeyWordResponseByCategoryDto {
    private final List<ReviewKeyWordInfo> keyWords;

    public ReviewKeyWordResponseByCategoryDto(final List<ReviewKeyWordInfo> reviewKeyWordResponseDto) {
        this.keyWords = reviewKeyWordResponseDto;
    }
}
