package travelfeeldog.domain.feed.feed.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.domain.feed.feed.dao.FeedImagesRepository;
import travelfeeldog.domain.feed.feed.dao.FeedRepository;
import travelfeeldog.domain.feed.feed.model.Feed;
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

    public Feed getFeedStaticsById(Long id) {
        Optional<Feed> feed = feedRepository.getFeedStaticData(id);
        return feed.orElseThrow(() -> new NoSuchElementException("Feed details loading is failed."));

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
