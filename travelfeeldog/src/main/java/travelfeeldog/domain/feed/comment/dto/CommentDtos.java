package travelfeeldog.domain.feed.comment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentDtos {
    @Data
    public static class CommentRequestDto{
        private Long feedId;
        private Long memberId;
        private String content;
    }
}
