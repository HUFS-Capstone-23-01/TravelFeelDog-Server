package travelfeeldog.domain.feed.comment.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import travelfeeldog.domain.feed.comment.dto.CommentDtos.CommentRequestDto;
import travelfeeldog.domain.feed.comment.service.CommentService;
import travelfeeldog.global.common.dto.ApiResponse;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentApiController {
    private final CommentService commentService ;
    @PostMapping(produces = "application/json;charset=UTF-8")
    public ApiResponse addNewComment(@RequestHeader("Authorization") String token, @RequestBody CommentRequestDto requestDto){
        return ApiResponse.success(commentService.postComment(requestDto));
    }
}
