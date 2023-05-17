package travelfeeldog.domain.feed.feed.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.domain.feed.feed.dao.FeedTagRepository;
import travelfeeldog.domain.feed.feed.model.Feed;
import travelfeeldog.domain.feed.tag.model.Tag;
import travelfeeldog.domain.feed.tag.service.TagService;

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
