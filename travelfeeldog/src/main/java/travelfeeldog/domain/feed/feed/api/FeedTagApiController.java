package travelfeeldog.domain.feed.feed.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import travelfeeldog.domain.feed.feed.dto.FeedDtos.FeedListResponseDto;
import travelfeeldog.domain.feed.feed.dto.FeedTagDtos.TagSearchRequestDto;
import travelfeeldog.domain.feed.feed.model.Feed;
import travelfeeldog.domain.feed.feed.service.FeedTagService;
import travelfeeldog.domain.feed.tag.model.Tag;
import travelfeeldog.global.common.dto.ApiResponse;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/feed/list")
@RequiredArgsConstructor
public class FeedTagApiController {

    private final FeedTagService feedTagService;

    @GetMapping(value = "/searchTag", produces = "application/json;charset=UTF-8")
    public ApiResponse getFeedListByTag(@RequestBody TagSearchRequestDto tagSearchRequestDto,
                                        @RequestParam("page") int page) {
        List<Tag> validTags = feedTagService.getValidTagsByContents(tagSearchRequestDto.getTagContents());
        if(validTags.isEmpty())
            return ApiResponse.success(new ArrayList<>());

        List<Feed> feeds = feedTagService.getListByTagContents(validTags, page);
        if(feeds.isEmpty())
            return ApiResponse.success(new ArrayList<>());

        List<FeedListResponseDto> lists = feeds.stream().map(FeedListResponseDto::new).toList();
        return ApiResponse.success(lists);
    }
}
