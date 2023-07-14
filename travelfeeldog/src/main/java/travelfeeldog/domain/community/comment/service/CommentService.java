package travelfeeldog.domain.community.comment.service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.domain.community.comment.dao.CommentRepository;
import travelfeeldog.domain.community.comment.dto.CommentDtos.CommentRequestDto;
import travelfeeldog.domain.community.comment.dto.CommentDtos.CommentResponseDto;
import travelfeeldog.domain.community.comment.model.Comment;
import travelfeeldog.domain.community.feed.model.Feed;
import travelfeeldog.domain.community.feed.service.FeedService;
import travelfeeldog.domain.member.model.Member;
import travelfeeldog.domain.member.service.MemberService;
import travelfeeldog.global.common.domain.model.BaseTimeEntity;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberService memberService;
    private final FeedService feedService;
    @Transactional
    public CommentResponseDto postComment(String token,CommentRequestDto requestDto){
        Member member = memberService.findByToken(token);
        Feed feed = feedService.findByFeedId(requestDto.getFeedId());
        Comment comment = new Comment();
        comment.setFeed(feed);
        comment.setContent(requestDto.getContent());
        comment.setMember(member);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }
    @Transactional
    public Boolean deleteComment(String token, Long commentId){
        Member member = memberService.findByToken(token);
        try{
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with ID"));
        if (Objects.equals(comment.getMember().getId(), member.getId())){
            commentRepository.delete(comment);
            return true;
        }
        else {
            return false;
        }
        }catch (EntityNotFoundException e){
            return false;
        }
    }
    public List<CommentResponseDto> getAllCommentByFeedId(String token , Long feedId){
        memberService.findByToken(token);
        List<Comment> comments = commentRepository.findAllByFeedId(feedId)
                .stream()
                .sorted(Comparator.comparing(BaseTimeEntity::getCreatedDateTime).reversed()).toList();
        return comments.stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }
}
