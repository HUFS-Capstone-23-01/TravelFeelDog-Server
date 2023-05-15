package travelfeeldog.domain.review.reviewkeyword.dto;

import java.util.List;
import lombok.Data;
import travelfeeldog.domain.review.keyword.dto.KeyWordDtos.BadKeyWordResponseDto;
import travelfeeldog.domain.review.keyword.dto.KeyWordDtos.GoodKeyWordResponseDto;

public class ReviewKeyWordDtos {
    @Data
    public static class ReviewGoodKeyWordResponseDto {
        private Long goodKeyWordId;
        private String goodKeyWordName;
        private int keyWordCount;
        public ReviewGoodKeyWordResponseDto(GoodKeyWordResponseDto goodKeyWord){
            this.goodKeyWordId =goodKeyWord.getGoodKeyWordId();
            this.goodKeyWordName = goodKeyWord.getGoodKeyWordName();
            keyWordCount = 0;
        }
    }
    @Data
    public static class ReviewBadKeyWordResponseDto {
        private Long badKeyWordId;
        private String badKeyWordName;
        private int keyWordCount;
        public ReviewBadKeyWordResponseDto(BadKeyWordResponseDto badKeyWord){
            this.badKeyWordId = badKeyWord.getBadKeyWordId();
            this.badKeyWordName = badKeyWord.getBadKeyWordName();
            keyWordCount = 0;
        }
    }
    @Data
    public static class ReviewKeyWordResponseByCategoryDto{
        private List<ReviewGoodKeyWordResponseDto> goodKeyWords;
        private List<ReviewBadKeyWordResponseDto> badKeyWords;
//        public ReviewKeyWordResponseByCategoryDto(List<GoodKeyWord> goodKeyWords ,List<BadKeyWord> badKeyWords){
//            this.goodKeyWords = goodKeyWords.stream().map(KeyWordDtos.GoodKeyWordResponseDto::new).collect(Collectors.toList());
//            this.badKeyWords = badKeyWords.stream().map(KeyWordDtos.BadKeyWordResponseDto::new).collect(Collectors.toList());
//        }
    }
}
