package travelfeeldog.domain.feed.comment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import travelfeeldog.domain.feed.comment.model.Comment;

@Data
@NoArgsConstructor
public class CommentDtos {
    @Data
    public static class CommentRequestDto{
        private Long feedId;
        private String content;
    }
    @Data
    public static class CommentResponseDto{
        private Long memberId;
        private String memberNickName;
        private String memberImageUrl;
        private String content;
        public CommentResponseDto(Comment comment){
            memberId = comment.getMember().getId();
            memberNickName = comment.getMember().getNickName();
            memberImageUrl = comment.getMember().getImageUrl();
            content = comment.getContent();
        }
    }
}
