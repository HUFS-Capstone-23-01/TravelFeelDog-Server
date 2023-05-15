package travelfeeldog.domain.review.reviewkeyword.dto;

import java.util.List;
import lombok.Data;
import travelfeeldog.domain.review.keyword.dto.KeyWordDtos.BadKeyWordResponseDto;
import travelfeeldog.domain.review.keyword.dto.KeyWordDtos.GoodKeyWordResponseDto;

public class ReviewKeyWordDtos {
    @Data
    public static class ReviewKeyWordResponseDto {
        private Long keyWordId;
        private String keyWordName;
        private int keyWordCount;
        public ReviewKeyWordResponseDto(GoodKeyWordResponseDto goodKeyWord){
            this.keyWordId =goodKeyWord.getGoodKeyWordId();
            this.keyWordName = goodKeyWord.getGoodKeyWordName();
            keyWordCount = 0;
        }
        public ReviewKeyWordResponseDto(BadKeyWordResponseDto badKeyWord){
            this.keyWordId = badKeyWord.getBadKeyWordId();
            this.keyWordName = badKeyWord.getBadKeyWordName();
            keyWordCount = 0;
        }
    }

    @Data
    public static class ReviewKeyWordResponseByCategoryDto{
        private List<ReviewKeyWordResponseDto> keyWords;

    }
}
