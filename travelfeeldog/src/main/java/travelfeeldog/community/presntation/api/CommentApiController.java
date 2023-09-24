package travelfeeldog.community.presntation.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import travelfeeldog.community.dto.CommentDtos.CommentRequestDto;
import travelfeeldog.community.dto.CommentDtos.CommentResponseDto;
import travelfeeldog.community.comment.domain.service.CommentService;
import travelfeeldog.global.common.dto.ApiResponse;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentApiController {
    private final CommentService commentService ;
    @PostMapping(produces = "application/json;charset=UTF-8")
    public ApiResponse<CommentResponseDto>addNewComment(@RequestHeader("Authorization") String token, @RequestBody CommentRequestDto requestDto){
        return ApiResponse.success(commentService.postComment(token,requestDto));
    }
    @GetMapping(value = "/all",produces = "application/json;charset=UTF-8")
    public ApiResponse<List<CommentResponseDto>> getAllCommentByFeedId(@RequestHeader("Authorization") String token, @RequestParam Long feedId){
        return ApiResponse.success(commentService.getAllCommentByFeedId(token,feedId));
    }
    @DeleteMapping(produces = "application/json;charset=UTF-8")
    public ApiResponse<Boolean> addNewComment(@RequestHeader("Authorization") String token, @RequestParam Long commentId){
        return ApiResponse.success(commentService.deleteComment(token,commentId));
    }
}
