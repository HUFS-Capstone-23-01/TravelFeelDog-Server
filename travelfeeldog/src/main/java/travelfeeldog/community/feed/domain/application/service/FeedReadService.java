package travelfeeldog.community.feed.domain.application.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.community.feed.repository.FeedRepository;
import travelfeeldog.community.dto.ScrapDtos.ScrapByMemberResponseDto;
import travelfeeldog.community.dto.FeedDtos.FeedStaticResponseDto;
import travelfeeldog.community.feed.domain.model.Feed;
import travelfeeldog.community.dto.FeedLikeDtos.FeedLikesByMemberResponseDto;
import travelfeeldog.member.domain.model.Member;
import travelfeeldog.member.domain.application.service.MemberService;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class FeedReadService {

    private final FeedRepository feedRepository;
    private final MemberService memberService;

    public Feed getFeedDetailsById(Long id) {
        return feedRepository.findFeedDetail(id)
                .orElseThrow(() -> new NoSuchElementException("Feed details loading is failed."));
    }

    // fix point
    public Boolean isFeedOwner(Long feedId, String requestMemberToken) {
        String givenFeedOwnerToken = getFeedDetailsById(feedId).getMember().getRoleKey();
        return givenFeedOwnerToken.equals(requestMemberToken);
    }

    public FeedStaticResponseDto getFeedStaticsById(Long id, String token) {
        Member member = memberService.findByToken(token);

        Feed feed = feedRepository.findFeedDetail(id)
                .orElseThrow(() -> new NoSuchElementException("Feed details loading is failed."));
        FeedStaticResponseDto feedStaticResponseDto = new FeedStaticResponseDto(feed);

        List<FeedLikesByMemberResponseDto> allMemberFeedLike = member.getFeedLikes().stream()
                .map(FeedLikesByMemberResponseDto::new).toList();
        List<ScrapByMemberResponseDto> allMemberScrap = member.getScraps().stream()
                .map(ScrapByMemberResponseDto::new).toList();

        Optional<ScrapByMemberResponseDto> doScrap = allMemberScrap.stream()
                .filter(scrapByMemberResponseDto ->
                        scrapByMemberResponseDto.getFeedId().equals(feed.getId()))
                .findFirst();
        Optional<FeedLikesByMemberResponseDto> doLike = allMemberFeedLike.stream()
                .filter(feedLikesByMemberResponseDto ->
                        feedLikesByMemberResponseDto.getFeedId().equals(feed.getId()))
                .findFirst();
        if (doScrap.isPresent()) {
            feedStaticResponseDto.setFeedScrapId(doScrap.get().getScrapId());
        }
        if (doLike.isPresent()) {
            feedStaticResponseDto.setFeedLikeId(doLike.get().getFeedLikeId());
        }
        return feedStaticResponseDto;
    }

    public List<Feed> getListAll(int page) {
        int offset = (page - 1) * 6;
        List<Feed> feeds = feedRepository.getListAll(offset);
        return feeds;
    }

    public List<Feed> getListByNickName(String nickName, int page) {
        int offset = (page - 1) * 6;
        List<Feed> feeds = feedRepository.findListByNickName(nickName, offset);
        return feeds;
    }

    public Feed findByFeedId(Long feedId) {
        return feedRepository.findById(feedId)
                .orElseThrow(() -> new EntityNotFoundException("Feed not found with ID"));
    }
}
