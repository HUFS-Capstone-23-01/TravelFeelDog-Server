package travelfeeldog.domain.keyword.dto;

import lombok.Data;
import travelfeeldog.domain.keyword.model.BadKeyWord;
import travelfeeldog.domain.keyword.model.GoodKeyWord;

public class KeyWordDtos {
    @Data
    public static class KeyWordResponseDto{
        String categoryName;
        String keyWord;
        public KeyWordResponseDto(GoodKeyWord goodKeyWord){
                this.keyWord = goodKeyWord.getKeyWord();
                this.categoryName = goodKeyWord.getCategory().getName();
        }
        public KeyWordResponseDto(BadKeyWord badKeyWord){
                this.keyWord = badKeyWord.getKeyWord();
                this.categoryName = badKeyWord.getCategory().getName();
        }
    }
}
