package travelfeeldog.aggregate.community.feed.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.aggregate.community.tag.model.Tag;
import travelfeeldog.aggregate.community.tag.service.TagService;
import travelfeeldog.aggregate.community.feed.dao.FeedTagRepository;
import travelfeeldog.aggregate.community.feed.model.Feed;

import java.util.List;
import java.util.stream.Collectors;


@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class FeedTagService {

    private final TagService tagService;
    private final FeedTagRepository feedTagRepository;


    public List<Tag> getValidTagsByContents(List<String> tagContents) {
        return tagService.findTagListByTagContents(tagContents);
    }

    public List<Tag> makeTagsByContents(List<String> tagContents) {
        return tagService.makeTagListByTagContents(tagContents);
    }

    public List<Feed> getListByTagContents(List<Tag> tags, int page) {
        int offset = (page-1) * 6;
        List<String> tagContents = tags.stream().map(tag -> tag.getTagContent()).collect(Collectors.toList());
        List<Feed> feeds = feedTagRepository.findByTagContents(tagContents, offset);
        return feeds;

    }
}
