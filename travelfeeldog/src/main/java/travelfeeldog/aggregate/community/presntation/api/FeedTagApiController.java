package travelfeeldog.aggregate.community.presntation.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import travelfeeldog.aggregate.community.dto.FeedDtos.FeedListResponseDto;
import travelfeeldog.aggregate.community.feed.domain.model.Feed;
import travelfeeldog.aggregate.community.feed.domain.application.service.FeedTagService;
import travelfeeldog.aggregate.community.feed.domain.model.Tag;
import travelfeeldog.global.common.dto.ApiResponse;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/feed/list")
@RequiredArgsConstructor
public class FeedTagApiController {

    private final FeedTagService feedTagService;

    @GetMapping(value = "/searchTag", produces = "application/json;charset=UTF-8")
    public ApiResponse getFeedListByTag(@RequestParam("tagContents")List<String> tagContents, @RequestParam("page") int page) {
        List<Tag> validTags = feedTagService.getValidTagsByContents(tagContents);
        if(validTags.isEmpty())
            return ApiResponse.success(new ArrayList<>());

        List<Feed> feeds = feedTagService.getListByTagContents(validTags, page);
        if(feeds.isEmpty())
            return ApiResponse.success(new ArrayList<>());

        List<FeedListResponseDto> lists = feeds.stream().map(FeedListResponseDto::new).toList();
        return ApiResponse.success(lists);
    }
}
