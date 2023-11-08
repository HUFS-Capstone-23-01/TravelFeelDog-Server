package travelfeeldog.community.scrap.service;

import java.util.List;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.community.feed.domain.model.Feed;
import travelfeeldog.community.feed.application.service.FeedReadService;
import travelfeeldog.community.scrap.domain.ScrapRepository;
import travelfeeldog.community.dto.ScrapDtos.ScrapByMemberResponseDto;
import travelfeeldog.community.dto.ScrapDtos.ScrapRequestDto;
import travelfeeldog.member.domain.model.Member;
import travelfeeldog.member.application.service.MemberService;
import travelfeeldog.community.scrap.domain.model.Scrap;


@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ScrapService {
    private final ScrapRepository scrapRepository;
    private final MemberService memberService;
    private final FeedReadService feedReadService;

    @Transactional
    public Boolean addNewScrap(String token, ScrapRequestDto requestDto) {
        Member member = memberService.findByToken(token);
        Long memberId = member.getId();
        Long feedId = requestDto.getFeedId();

        List<Scrap> existingScraps = scrapRepository.findScrapsByMemberIdAndFeedId(memberId, feedId);
        if (!existingScraps.isEmpty()) {
            return false;
        }
        Feed feed = feedReadService.findByFeedId(feedId);
        feed.updateScrapCountPlus(true);
        Scrap scrap = new Scrap(member, feed);
        scrapRepository.save(scrap);
        return true;
    }


    public List<ScrapByMemberResponseDto> getAllMemberScrap(String token) {
        Member member = memberService.findByToken(token);
        return scrapRepository.findAllByMemberId(member.getId()).stream().map(ScrapByMemberResponseDto::new).toList();
    }

    @Transactional
    public Boolean deleteScrap(String token, Long scrapId) {
        Member member = memberService.findByToken(token);
        Scrap scrap = scrapRepository.findById(scrapId)
                .orElseThrow(() -> new EntityNotFoundException("Scrap not found with ID" + scrapId));
        scrap.getFeed().updateScrapCountPlus(false);
        if (scrap.checkMember(member)) {
            scrapRepository.delete(scrap);
            return true;
        } else {
            return false;
        }
    }
}
