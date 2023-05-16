package travelfeeldog.domain.feed.scrap.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.domain.feed.feed.dto.FeedDtos.FeedCollectByMemberDetailResponseDto;
import travelfeeldog.domain.feed.feed.service.FeedService;
import travelfeeldog.domain.feed.scrap.dao.ScrapRepository;
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
    public Boolean addNewScrap(String token ,ScrapRequestDto requestDto) {
        Scrap scrap = new Scrap(memberService.findByToken(token),feedService.findByFeedId(requestDto.getFeedId()));
        if(scrapRepository.isNew(scrap)){
            scrapRepository.save(scrap);
            return true;
        }
        return false;
    }
    public List<FeedCollectByMemberDetailResponseDto> getAllMemberScrap(String token) {
        Member member = memberService.findByToken(token);
        return scrapRepository.findAllFeedByMemberId(member.getId()).stream().map(FeedCollectByMemberDetailResponseDto::new).toList();
    }
}
