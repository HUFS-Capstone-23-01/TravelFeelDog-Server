package travelfeeldog.domain.feed.feed.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.domain.feed.FeedLike.model.FeedLike;
import travelfeeldog.domain.feed.feed.dao.FeedRepository;
import travelfeeldog.domain.feed.feed.dto.FeedDtos.FeedStaticResponseDto;
import travelfeeldog.domain.feed.feed.model.Feed;
import travelfeeldog.domain.feed.scrap.model.Scrap;
import travelfeeldog.domain.member.dao.MemberRepository;
import travelfeeldog.domain.member.model.Member;
import travelfeeldog.domain.feed.tag.dao.TagRepository;
import travelfeeldog.domain.feed.tag.model.Tag;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;
    private final MemberRepository memberRepository;
    private final TagRepository tagRepository;

    @Transactional
    public Feed postFeed(String writerToken,
                         String title,
                         String body,
                         List<String> feedImagesUrls,
                         List<String> tagContents) {
        Member writer = memberRepository.findByToken(writerToken).get();
        List<Tag> tags = new ArrayList<>();

        for(String tagContent : tagContents) {
            Optional<Tag> tag = tagRepository.findByTagContent(tagContent);
            if(tag.isPresent()) {
                tags.add(tag.get());
            }
            else {
                tags.add(tagRepository.save(tagContent));
            }
        }

        Feed result;
        int imagesExist = feedImagesUrls.isEmpty() ? 0 : 2; //10(binary)
        int tagsExist = tagContents.isEmpty() ? 0 : 1; //01(binary)
        switch(imagesExist | tagsExist) {
            case 3:
                result = feedRepository.save(writer, feedImagesUrls, title, body, tags);
                break;
            case 2:
                result = feedRepository.save(writer, feedImagesUrls, title, body);
                break;
            case 1:
                result = feedRepository.save(writer, title, body, tags);
                break;
            default:
                result = feedRepository.save(writer, title, body);
                break;
        }
        return result;
    }

    public Feed getFeedDetailsById(Long id) {
        Feed feed = feedRepository.findFeedDetail(id)
                .orElseThrow(() -> new NoSuchElementException("Feed details loading is failed."));
        return feed;
    }


    public FeedStaticResponseDto getFeedStaticsById(Long id) {
        Feed feed = feedRepository.findFeedDetail(id)
                .orElseThrow(() -> new NoSuchElementException("Feed details loading is failed."));
        FeedStaticResponseDto feedStaticResponseDto = new FeedStaticResponseDto(feed);
        List<Scrap> feedScraps = feed.getFeedScraps();
        List<FeedLike> feedLikes = feed.getFeedLikes();
        int doScrap = feedScraps.indexOf(new Scrap(feed.getMember(), feed));
        int doLike = feedLikes.indexOf(new Scrap(feed.getMember(), feed));
        if(doScrap != -1) {
            feedStaticResponseDto.setDoScrap(true);
            feedStaticResponseDto.setFeedScrapId(feedScraps.get(doScrap).getId());
        }
        if(doLike != -1) {
            feedStaticResponseDto.setDolike(true);
            feedStaticResponseDto.setFeedLikeId(feedLikes.get(doLike).getId());
        }
        return feedStaticResponseDto;
    }

    @Transactional
    public void deleteFeed(Long id) { feedRepository.deleteById(id); }

    public List<Feed> getListAll(int page) {
        int offset = (page-1) * 6;
        List<Feed> feeds = feedRepository.getListAll(offset);
        return feeds;
    }

    public List<Feed> getListByNickName(String nickName, int page) {
        int offset = (page-1) * 6;
        List<Feed> feeds = feedRepository.findListByNickName(nickName, offset);
        return feeds;
    }

    public Feed findByFeedId(Long feedId){
        return feedRepository.findById(feedId)
                .orElseThrow(() -> new EntityNotFoundException("Feed not found with ID"));
    }

}
