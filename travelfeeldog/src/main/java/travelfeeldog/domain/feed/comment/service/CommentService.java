package travelfeeldog.domain.feed.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.domain.feed.comment.dao.CommentRepository;
import travelfeeldog.domain.feed.comment.dto.CommentDtos.CommentRequestDto;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public void postComment(CommentRequestDto requestDto){

    }
    public void deleteComment(){

    }
    public void getAllcommentByPlaceId(){

    }
}
