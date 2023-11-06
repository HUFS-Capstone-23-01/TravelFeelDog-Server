package travelfeeldog.community.comment.domain.service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.community.comment.repository.CommentRepository;
import travelfeeldog.community.comment.domain.model.Comment;
import travelfeeldog.community.feed.domain.model.Feed;
import travelfeeldog.community.feed.domain.application.service.FeedReadService;
import travelfeeldog.member.domain.model.Member;
import travelfeeldog.member.domain.application.service.MemberService;
import travelfeeldog.community.dto.CommentDtos.CommentRequestDto;
import travelfeeldog.community.dto.CommentDtos.CommentResponseDto;
import travelfeeldog.global.common.domain.basetime.BaseTimeEntity;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberService memberService;
    private final FeedReadService feedReadService;

    @Transactional
    public CommentResponseDto postComment(String token, CommentRequestDto requestDto) {
        Member member = memberService.findByToken(token);
        Feed feed = feedReadService.findByFeedId(requestDto.getFeedId());
        Comment comment = new Comment();
        comment.setFeed(feed);
        comment.setContent(requestDto.getContent());
        comment.setMember(member);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public Boolean deleteComment(String token, Long commentId) {
        Member member = memberService.findByToken(token);
        try {
            Comment comment = commentRepository.findById(commentId)
                    .orElseThrow(() -> new EntityNotFoundException("Comment not found with ID"));
            if (Objects.equals(comment.getMember().getId(), member.getId())) {
                commentRepository.delete(comment);
                return true;
            } else {
                return false;
            }
        } catch (EntityNotFoundException e) {
            return false;
        }
    }

    public List<CommentResponseDto> getAllCommentByFeedId(String token, Long feedId) {
        memberService.findByToken(token);
        List<Comment> comments = commentRepository.findAllByFeedId(feedId)
                .stream()
                .sorted(Comparator.comparing(BaseTimeEntity::getCreatedDateTime).reversed()).toList();
        return comments.stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }
}
