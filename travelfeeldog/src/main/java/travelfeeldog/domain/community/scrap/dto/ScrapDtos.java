package travelfeeldog.domain.community.scrap.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import travelfeeldog.domain.community.scrap.model.Scrap;

public class ScrapDtos {
    
    @Data
    @NoArgsConstructor
    public static class ScrapRequestDto{
        private Long feedId;
    }
    @Data
    public static class ScrapByMemberResponseDto{
        private Long scrapId;
        private Long feedId;
        private String title;
        private String body;
        public ScrapByMemberResponseDto(Scrap scrap){
            this.scrapId = scrap.getId();
            this.feedId = scrap.getFeed().getId();
            this.title = scrap.getFeed().getTitle();
            this.body = scrap.getFeed().getBody();
        }
    }
}
