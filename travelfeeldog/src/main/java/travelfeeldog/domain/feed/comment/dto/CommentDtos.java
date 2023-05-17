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
        private Long commentId;
        private Long memberId;
        private String memberNickName;
        private int memberLevel;
        private String memberImageUrl;
        private String content;
        public CommentResponseDto(Comment comment){
            this.commentId = comment.getId();
            this.memberId = comment.getMember().getId();
            this.memberNickName = comment.getMember().getNickName();
            this.memberLevel = comment.getMember().getLevel();
            this.memberImageUrl = comment.getMember().getImageUrl();
            this.content = comment.getContent();
        }
    }
}
