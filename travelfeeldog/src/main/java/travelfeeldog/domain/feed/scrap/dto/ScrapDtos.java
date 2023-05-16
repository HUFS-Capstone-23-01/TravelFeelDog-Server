package travelfeeldog.domain.feed.scrap.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

public class ScrapDtos {
    
    @Data
    @NoArgsConstructor
    public static class ScrapRequestDto{
        private Long FeedId; 
    }
    
}
