package travelfeeldog.domain.community.feed.application.usecase;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travelfeeldog.domain.community.feed.dto.FeedDtos.FeedPostRequestDto;
import travelfeeldog.domain.community.feed.dto.FeedDtos.FeedStaticResponseDto;
import travelfeeldog.domain.community.feed.model.Feed;
import travelfeeldog.domain.community.feed.model.Tag;
import travelfeeldog.domain.community.feed.service.FeedTagService;
import travelfeeldog.domain.community.feed.service.FeedWriteService;
import travelfeeldog.domain.member.domain.model.Member;
import travelfeeldog.domain.member.domain.service.MemberService;

@Service
@RequiredArgsConstructor
public class CreateFeedUsecase {

    private final MemberService memberService;
    private final FeedTagService feedTagService;
    private final FeedWriteService feedWriteService;

    public FeedStaticResponseDto execute(FeedPostRequestDto requestDto) {
        Member writer = memberService.findByToken(requestDto.getMemberToken());
        List<Tag> tags = feedTagService.getTagsByContents(requestDto.getFeedTags());
        Feed feed = feedWriteService.postFeed(requestDto, writer, tags);
        return new FeedStaticResponseDto(feed);
    }

}
