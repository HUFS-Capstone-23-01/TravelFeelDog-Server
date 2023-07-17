package travelfeeldog.aggregate.community.scrap.service;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.aggregate.community.scrap.dao.ScrapRepository;
import travelfeeldog.aggregate.community.scrap.dto.ScrapDtos.ScrapByMemberResponseDto;
import travelfeeldog.aggregate.community.scrap.dto.ScrapDtos.ScrapRequestDto;
import travelfeeldog.aggregate.community.scrap.model.Scrap;
import travelfeeldog.aggregate.member.domain.model.Member;
import travelfeeldog.aggregate.member.domain.application.MemberService;
import travelfeeldog.aggregate.community.feed.model.Feed;
import travelfeeldog.aggregate.community.feed.service.FeedService;

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
    public Boolean deleteScrap(String token,Long scrapId) {
        Member member = memberService.findByToken(token);
        Scrap  scrap = scrapRepository.findById(scrapId)
                .orElseThrow(() -> new EntityNotFoundException("Scrap not found with ID"+scrapId));
        scrap.getFeed().updateScrapCountPlus(false);
        if(scrap.checkMember(member)) {
            scrapRepository.delete(scrap);
            return true;
        }else {
            return false;
        }
   }
}
