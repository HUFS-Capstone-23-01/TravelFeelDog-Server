package travelfeeldog.domain.feed.feed.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class FeedTagDtos {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class TagSearchRequestDto {
        List<String> tagContents;
    }

}
