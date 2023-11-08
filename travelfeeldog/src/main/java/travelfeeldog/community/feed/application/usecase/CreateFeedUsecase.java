package travelfeeldog.community.feed.application.usecase;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travelfeeldog.community.feed.application.service.FeedWriteService;
import travelfeeldog.community.feed.application.service.FeedTagService;
import travelfeeldog.member.application.service.MemberService;
import travelfeeldog.community.dto.FeedDtos.FeedPostRequestDto;
import travelfeeldog.community.dto.FeedDtos.FeedStaticResponseDto;
import travelfeeldog.community.feed.domain.model.Feed;
import travelfeeldog.community.feed.domain.model.Tag;
import travelfeeldog.member.domain.model.Member;

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
