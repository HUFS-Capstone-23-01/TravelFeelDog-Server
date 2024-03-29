package travelfeeldog.community.presntation.api;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import travelfeeldog.community.feed.application.usecase.CreateFeedUsecase;
import travelfeeldog.community.dto.FeedDtos.FeedPostRequestDto;
import travelfeeldog.community.dto.FeedDtos.FeedListResponseDto;
import travelfeeldog.community.feed.domain.model.Feed;
import travelfeeldog.community.feed.application.service.FeedReadService;

import travelfeeldog.community.feed.application.service.FeedWriteService;
import travelfeeldog.global.common.dto.ApiResponse;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import travelfeeldog.global.file.domain.application.ImageFileService;
import travelfeeldog.global.file.dto.ImageDtos.ImageDto;

@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
public class FeedApiController {
    private final FeedReadService feedReadService;
    private final FeedWriteService feedWriteService;
    private final CreateFeedUsecase createFeedUsecase;
    private final ImageFileService imageFileService;

    @PostMapping(value = "/post", produces = "application/json;charset=UTF-8")
    public ApiResponse postFeed(@Valid @RequestBody FeedPostRequestDto requestDto) {
        return ApiResponse.success(createFeedUsecase.execute(requestDto));
    }

    @PostMapping(value = "/post/uploadImage", produces = "application/json;charset=UTF-8")
    public ApiResponse postImageUrlForFeed(@Valid @RequestParam("file") MultipartFile[] files) {
        try {
            List<ImageDto> results = imageFileService.uploadImageFiles(files, "Feed");
            List<String> urls = results.stream().map(ImageDto::getFileName).toList();
            return ApiResponse.success(urls);
        } catch (RuntimeException e) {
            return ApiResponse.fail("Invalid Files");
        }
    }

    @DeleteMapping(value = "/detail", produces = "application/json;charset=UTF-8")
    public ApiResponse<Boolean> deleteFeedById(@RequestHeader("Authorization") String fireBaseToken,
                                               @RequestParam("feedId") Long feedId) {
        Boolean properFeedOwner = feedReadService.isFeedOwner(feedId, fireBaseToken);
        if (properFeedOwner) {
            feedWriteService.deleteFeed(feedId);
        }
        return ApiResponse.success(properFeedOwner);
    }

    @GetMapping(value = "/list", produces = "application/json;charset=UTF-8")
    public ApiResponse getFeedList(@RequestParam("page") int page) {
        List<FeedListResponseDto> list = new ArrayList<>();
        list.addAll(feedReadService.getListAll(page).stream().map(FeedListResponseDto::new).toList());
        return ApiResponse.success(list);
    }

    @GetMapping(value = "/list/searchNickName", produces = "application/json;charset=UTF-8")
    public ApiResponse getFeedListByNickName(
            @RequestParam("nickName") String nickName,
            @RequestParam("page") int page) {
        List<Feed> feeds = feedReadService.getListByNickName(nickName, page);
        if (feeds.isEmpty()) {
            return ApiResponse.success(new ArrayList<>());
        }
        List<FeedListResponseDto> result = feeds.stream().map(FeedListResponseDto::new).toList();
        return ApiResponse.success(result);
    }

    @GetMapping(value = "/detail/static", produces = "application/json;charset=UTF-8")
    public ApiResponse getFeedStaticById(@RequestParam(value = "feedId") Long id,
                                         @RequestHeader("Authorization") String token) {
        try {
            return ApiResponse.success(feedReadService.getFeedStaticsById(id, token));
        } catch (NoSuchElementException e) {
            return ApiResponse.success(false);
        }
    }
}
