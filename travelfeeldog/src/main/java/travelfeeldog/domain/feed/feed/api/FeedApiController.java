package travelfeeldog.domain.feed.feed.api;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import travelfeeldog.domain.feed.feed.dto.FeedDtos.FeedStaticResponseDto;
import travelfeeldog.domain.feed.feed.dto.FeedDtos.FeedListResponseDto;
import travelfeeldog.domain.feed.feed.dto.FeedDtos;
import travelfeeldog.domain.feed.feed.model.Feed;
import travelfeeldog.domain.feed.feed.service.FeedService;
import travelfeeldog.global.common.dto.ApiResponse;
import travelfeeldog.infra.aws.s3.dto.AwsS3ImageDtos.ImageDto;
import travelfeeldog.infra.aws.s3.service.AwsS3ImageService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
public class FeedApiController {
    private final FeedService feedService;
    private final AwsS3ImageService awsS3ImageService;

    @PostMapping(value = "/post", produces = "application/json;charset=UTF-8")
    public ApiResponse postFeed(@Valid @RequestBody FeedDtos.FeedPostRequestDto feedPostRequestDto,
                                @RequestParam("urls") List<String> imageUrls,
                                @RequestParam("tags") List<String> tags) throws Exception {
        Feed feed = feedService.postFeed(feedPostRequestDto.getMemberToken(),
                feedPostRequestDto.getTitle(),
                feedPostRequestDto.getBody(),
                imageUrls,
                tags);
        return ApiResponse.success(new FeedStaticResponseDto(feed));
    }

    @PostMapping(value = "/post/uploadImage", produces = "application/json;charset=UTF-8")
    public ApiResponse postImageUrlForFeed(@Valid @RequestParam("file") MultipartFile[] files) {
        try {
            List<ImageDto> results = awsS3ImageService.uploadImagesOnly(files, "Feed");
            List<String> urls = results.stream().map(ImageDto::getFileUrl).toList();
            return ApiResponse.success(urls);
        } catch (RuntimeException e) {
            return ApiResponse.fail("Invalid Files");
        }
    }

    @DeleteMapping(value = "/detail", produces = "application/json;charset=UTF-8")
    public ApiResponse deleteFeedById(@RequestHeader("Authorization") String firebaseToken,
                                      @RequestParam("feedId") String id) {
        Feed feed = feedService.getFeedStaticsById(id);

        if(feed.getMember().getToken().equals(firebaseToken)) {
            feedService.deleteFeed(id);
            return ApiResponse.success(true);
        }
        return ApiResponse.invalidAccessToken();
    }

    @GetMapping(value = "/list",produces = "application/json;charset=UTF-8")
    public ApiResponse getFeedList(@RequestParam("page") int page) {
        List<FeedListResponseDto> list = new ArrayList<>();
        list.addAll(feedService.getListAll(page).stream().map(FeedListResponseDto::new).toList());
        return ApiResponse.success(list);
    }
    /* Progress will be in FeedTagApiController
    @GetMapping(value = "/list/searchTag", produces = "application/json;charset=UTF-8")
    public ApiResponse getFeedListByTag() {
        FeedListResponseDto lists = feedService.getListByTag();
        return ApiResponse.success(lists);
    }
    */

    @GetMapping(value = "/list/searchNickName", produces = "application/json;charset=UTF-8")
    public ApiResponse getFeedListByNickName(@RequestParam("nickName") String nickName) {
        List<Feed> feeds = feedService.getListByNickName(nickName);
        if(feeds.isEmpty()) {
            return ApiResponse.success(false);
        }
        List<FeedListResponseDto> result = feeds.stream().map(FeedListResponseDto::new).toList();
        return ApiResponse.success(result);
    }

    @GetMapping(value = "/detail/static", produces = "application/json;charset=UTF-8")
    public ApiResponse getFeedStaticById(@RequestParam(value = "id") String id) {
        try {
            Feed feed = feedService.getFeedStaticsById(id);
            return ApiResponse.success(new FeedStaticResponseDto(feed));
        } catch (NoSuchElementException e) {
            return ApiResponse.success(false);
        }
    }

    /* Progress will be in Comment
    @GetMapping(value = "/detail/comments", produces = "application/json;charset=UTF-8")
    public ApiResponse getFeedCommentsById(@RequestParam("id") String id) {
        return ApiResponse.success(new FeedCommentsResponseDto(feedService.getFeedCommentsById(id)));
    }
    */
}
