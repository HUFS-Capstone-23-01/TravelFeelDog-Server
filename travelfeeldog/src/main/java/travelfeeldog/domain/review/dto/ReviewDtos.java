package travelfeeldog.domain.review.dto;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import travelfeeldog.domain.review.model.RecommendStatus;

public class ReviewDtos {
    @Data
    @NoArgsConstructor
    public static class ReviewPostRequestDto{
        Long placeId;
        String additionalScript;
        RecommendStatus recommendStatus; // GOOD,IDK,BAD
        int smallDogNumber;
        int mediumDogNumber;
        int largeDogNumber;
        List<String> imageUrls ;
        List<Long> goodKeyWordIds;
        List<Long> badKeyWordIds;
    }
}
