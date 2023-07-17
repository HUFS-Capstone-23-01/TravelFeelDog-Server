package travelfeeldog.aggregate.review.keyword.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import travelfeeldog.aggregate.review.keyword.model.BadKeyWord;
import travelfeeldog.aggregate.review.keyword.model.GoodKeyWord;

public class KeyWordDtos {
    @Data
    public static class KeyWordResponseDto{
        private String categoryName;
        private String keyWordName;
        public KeyWordResponseDto(GoodKeyWord goodKeyWord){
                this.keyWordName = goodKeyWord.getKeyWordName();
                this.categoryName = goodKeyWord.getCategory().getName();
        }
        public KeyWordResponseDto(BadKeyWord badKeyWord){
                this.keyWordName = badKeyWord.getKeyWordName();
                this.categoryName = badKeyWord.getCategory().getName();
        }
    }
    @Data
    public static class GoodKeyWordResponseDto {
        private Long goodKeyWordId;
        private String goodKeyWordName;
        public GoodKeyWordResponseDto(GoodKeyWord goodKeyWord){
            this.goodKeyWordId =goodKeyWord.getId();
            this.goodKeyWordName = goodKeyWord.getKeyWordName();
        }
    }
    @Data
    public static class BadKeyWordResponseDto {
        private Long badKeyWordId;
        private String badKeyWordName;
        public BadKeyWordResponseDto(BadKeyWord badKeyWord){
            this.badKeyWordId = badKeyWord.getId();
            this.badKeyWordName = badKeyWord.getKeyWordName();
        }
    }
    @Data
    public static class KeyWordResponseByCategoryDto{
        private List<GoodKeyWordResponseDto> goodKeyWords;
        private List<BadKeyWordResponseDto> badKeyWords;
        public KeyWordResponseByCategoryDto(List<GoodKeyWord> goodKeyWords ,List<BadKeyWord> badKeyWords){
            this.goodKeyWords = goodKeyWords.stream().map(GoodKeyWordResponseDto::new).collect(Collectors.toList());
            this.badKeyWords = badKeyWords.stream().map(BadKeyWordResponseDto::new).collect(Collectors.toList());
        }
    }
}
