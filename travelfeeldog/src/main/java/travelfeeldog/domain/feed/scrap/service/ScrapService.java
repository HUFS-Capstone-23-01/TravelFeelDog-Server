package travelfeeldog.domain.feed.scrap.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.domain.feed.feed.dto.FeedDtos.FeedCollectByMemberDetailResponseDto;
import travelfeeldog.domain.feed.feed.model.Feed;
import travelfeeldog.domain.feed.feed.service.FeedService;
import travelfeeldog.domain.feed.scrap.dao.ScrapRepository;
import travelfeeldog.domain.feed.scrap.dto.ScrapDtos.ScrapByMemberResponseDto;
import travelfeeldog.domain.feed.scrap.dto.ScrapDtos.ScrapRequestDto;
import travelfeeldog.domain.feed.scrap.model.Scrap;
import travelfeeldog.domain.member.model.Member;
import travelfeeldog.domain.member.service.MemberService;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ScrapService {
    private final ScrapRepository scrapRepository;
    private final MemberService memberService;
    private final FeedService feedService;
    @Transactional
    public Boolean addNewScrap(String token, ScrapRequestDto requestDto) {
        Member member = memberService.findByToken(token);
        Long memberId = member.getId();
        Long feedId = requestDto.getFeedId();

        List<Scrap> existingScraps = scrapRepository.findScrapsByMemberIdAndFeedId(memberId, feedId);
        if (!existingScraps.isEmpty()) {
            return false;
        }
        Feed feed = feedService.findByFeedId(feedId);
        Scrap scrap = new Scrap(member, feed);
        scrapRepository.save(scrap);
        return true;
    }


    public List<ScrapByMemberResponseDto> getAllMemberScrap(String token) {
        Member member = memberService.findByToken(token);
        return scrapRepository.findAllByMemberId(member.getId()).stream().map(ScrapByMemberResponseDto::new).toList();
    }
}
