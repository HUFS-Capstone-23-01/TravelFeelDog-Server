package travelfeeldog.domain.feed.FeedLike.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.domain.feed.FeedLike.dao.FeedLikeRepository;
import travelfeeldog.domain.feed.FeedLike.dto.FeedLikeDtos.FeedLikeRequestDto;
import travelfeeldog.domain.feed.FeedLike.dto.FeedLikeDtos.FeedLikesByMemberResponseDto;
import travelfeeldog.domain.feed.FeedLike.model.FeedLike;
import travelfeeldog.domain.feed.feed.model.Feed;
import travelfeeldog.domain.feed.feed.service.FeedService;
import travelfeeldog.domain.member.model.Member;
import travelfeeldog.domain.member.service.MemberService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class FeedLikeService {
    private final FeedLikeRepository feedLikeRepository;
    private final MemberService memberService;
    private final FeedService feedService;
    @Transactional
    public Boolean addNewScrap(String token, FeedLikeRequestDto requestDto) {
        Member member = memberService.findByToken(token);
        Long memberId = member.getId();
        Long feedId = requestDto.getFeedId();

        List<FeedLike> existingScraps = feedLikeRepository.findFeedLikesByMemberIdAndFeedId(memberId, feedId);
        if (!existingScraps.isEmpty()) {
            return false;
        }
        Feed feed = feedService.findByFeedId(feedId);
        feed.updateFeedLikeCountPlus(true);
        FeedLike feedLike = new FeedLike(member, feed);
        feedLikeRepository.save(feedLike);
        return true;
    }


    public List<FeedLikesByMemberResponseDto> getAllMemberFeedLike(String token) {
        Member member = memberService.findByToken(token);
        return feedLikeRepository.findAllByMemberId(member.getId()).stream()
                .map(FeedLikesByMemberResponseDto::new).toList();
    }
    public Boolean deleteFeedLike(String token,Long feedLikeId){
        memberService.findByToken(token);
        FeedLike  feedLike = feedLikeRepository.findById(feedLikeId)
                .orElseThrow(() -> new EntityNotFoundException("Scrap not found with ID"+feedLikeId));
        feedLike.getFeed().updateFeedLikeCountPlus(false);
        feedLikeRepository.delete(feedLike);
        return true;
   }
}
