package travelfeeldog.review.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import travelfeeldog.review.dto.KeyWordDtos.BadKeyWordResponseDto;
import travelfeeldog.review.dto.KeyWordDtos.GoodKeyWordResponseDto;

public class ReviewKeyWordDtos {
    @Getter
    @Setter
    public static class ReviewKeyWordResponseDto {
        private final Long keyWordId;
        private final String keyWordName;
        private int keyWordCount;

        public ReviewKeyWordResponseDto(GoodKeyWordResponseDto goodKeyWord) {
            this.keyWordId = goodKeyWord.getGoodKeyWordId();
            this.keyWordName = goodKeyWord.getGoodKeyWordName();
            this.keyWordCount = 0;
        }

        public ReviewKeyWordResponseDto(BadKeyWordResponseDto badKeyWord) {
            this.keyWordId = badKeyWord.getBadKeyWordId();
            this.keyWordName = badKeyWord.getBadKeyWordName();
            this.keyWordCount = 0;
        }
    }

    @Getter
    public static class ReviewKeyWordResponseByCategoryDto {
        private List<ReviewKeyWordResponseDto> keyWords;

        public ReviewKeyWordResponseByCategoryDto(List<ReviewKeyWordResponseDto> reviewKeyWordResponseDto) {
            this.keyWords = reviewKeyWordResponseDto;
        }
    }
}
