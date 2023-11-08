package travelfeeldog.community.feedlike.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.community.feed.domain.model.Feed;
import travelfeeldog.community.feed.domain.application.service.FeedReadService;
import travelfeeldog.community.feedlike.repository.FeedLikeRepository;
import travelfeeldog.community.dto.FeedLikeDtos.FeedLikeRequestDto;
import travelfeeldog.community.dto.FeedLikeDtos.FeedLikesByMemberResponseDto;
import travelfeeldog.community.feedlike.domain.model.FeedLike;
import travelfeeldog.member.domain.model.Member;
import travelfeeldog.member.application.service.MemberService;


import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class FeedLikeService {
    private final FeedLikeRepository feedLikeRepository;
    private final MemberService memberService;
    private final FeedReadService feedReadService;

    @Transactional
    public Boolean addNewScrap(String token, FeedLikeRequestDto requestDto) {
        Member member = memberService.findByToken(token);
        Long memberId = member.getId();
        Long feedId = requestDto.getFeedId();

        List<FeedLike> existingScraps = feedLikeRepository.findFeedLikesByMemberIdAndFeedId(memberId, feedId);
        if (!existingScraps.isEmpty()) {
            return false;
        }
        Feed feed = feedReadService.findByFeedId(feedId);
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

    @Transactional
    public Boolean deleteFeedLike(String token, Long feedLikeId) {
        Member member = memberService.findByToken(token);
        FeedLike feedLike = feedLikeRepository.findById(feedLikeId)
                .orElseThrow(() -> new EntityNotFoundException("Scrap not found with ID" + feedLikeId));
        feedLike.getFeed().updateFeedLikeCountPlus(false);
        if (member.getId().equals(feedLike.getMember().getId())) {
            feedLikeRepository.delete(feedLike);
            return true;
        } else {
            return false;
        }
    }
}
