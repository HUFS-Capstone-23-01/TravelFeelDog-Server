package travelfeeldog.aggregate.community.feed.domain.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.aggregate.community.feed.repository.FeedRepository;
import travelfeeldog.aggregate.community.dto.FeedDtos.FeedPostRequestDto;
import travelfeeldog.aggregate.community.feed.domain.model.Feed;
import travelfeeldog.aggregate.community.feed.domain.model.Tag;
import travelfeeldog.aggregate.member.domain.model.Member;

@Transactional
@Service
@RequiredArgsConstructor
public class FeedWriteService {

    private final FeedRepository feedRepository;
    public Feed postFeed(FeedPostRequestDto requestDto, Member writer, List<Tag> tags) {
        Feed feed = Feed.create(writer,requestDto.getTitle(),requestDto.getBody());
        feed.setFeedImages(requestDto.getFeedImageUrls());
        feed.setTags(tags);

        return feedRepository.save(feed);
    }
    public void deleteFeed(Long feedId) {
        Feed feed = feedRepository.findById(feedId).orElseThrow();
        feed.deleteFeed();
    }

}
