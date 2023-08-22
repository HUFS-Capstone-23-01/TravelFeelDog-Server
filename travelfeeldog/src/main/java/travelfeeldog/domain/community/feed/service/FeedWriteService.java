package travelfeeldog.domain.community.feed.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.domain.community.feed.dao.FeedRepository;
import travelfeeldog.domain.community.feed.dto.FeedDtos.FeedPostRequestDto;
import travelfeeldog.domain.community.feed.model.Feed;
import travelfeeldog.domain.community.feed.model.Tag;
import travelfeeldog.domain.member.domain.model.Member;

@Transactional
@Service
@RequiredArgsConstructor
public class FeedWriteService {

    private final FeedRepository feedRepository;
    public Feed postFeed(FeedPostRequestDto requestDto, Member writer, List<Tag> tags) {

        Feed feed = Feed.create(writer, requestDto.getTitle(), requestDto.getBody());
        feed.setFeedImages(requestDto.getFeedImageUrls());
        feed.setTags(tags);

        return feedRepository.save(feed);
    }

    public void deleteFeed(Long id) {
        feedRepository.deleteById(id);
    }

}
