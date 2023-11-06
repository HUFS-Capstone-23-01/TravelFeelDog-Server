package travelfeeldog.review.dto;

import java.util.List;
import lombok.Data;
import lombok.Getter;
import travelfeeldog.review.domain.keyword.model.BadKeyWord;
import travelfeeldog.review.domain.keyword.model.GoodKeyWord;

public class KeyWordDtos {
    @Getter
    public static class KeyWordResponseDto {
        private final String categoryName;
        private final String keyWordName;

        public KeyWordResponseDto(final GoodKeyWord goodKeyWord) {
            this.keyWordName = goodKeyWord.getKeyWordName();
            this.categoryName = goodKeyWord.getCategory().getName();
        }

        public KeyWordResponseDto(final BadKeyWord badKeyWord) {
            this.keyWordName = badKeyWord.getKeyWordName();
            this.categoryName = badKeyWord.getCategory().getName();
        }
    }

    @Getter
    public static class GoodKeyWordResponseDto {
        private final Long goodKeyWordId;
        private final String goodKeyWordName;

        public GoodKeyWordResponseDto(final GoodKeyWord goodKeyWord) {
            this.goodKeyWordId = goodKeyWord.getId();
            this.goodKeyWordName = goodKeyWord.getKeyWordName();
        }
    }

    @Getter
    @Data
    public static class BadKeyWordResponseDto {
        private final Long badKeyWordId;
        private final String badKeyWordName;

        public BadKeyWordResponseDto(final BadKeyWord badKeyWord) {
            this.badKeyWordId = badKeyWord.getId();
            this.badKeyWordName = badKeyWord.getKeyWordName();
        }
    }

    @Getter
    public static class KeyWordResponseByCategoryDto {
        private final List<GoodKeyWordResponseDto> goodKeyWords;
        private final List<BadKeyWordResponseDto> badKeyWords;

        public KeyWordResponseByCategoryDto(final List<GoodKeyWord> goodKeyWords, final List<BadKeyWord> badKeyWords) {
            this.goodKeyWords = goodKeyWords.stream().map(GoodKeyWordResponseDto::new).toList();
            this.badKeyWords = badKeyWords.stream().map(BadKeyWordResponseDto::new).toList();
        }
    }
}
