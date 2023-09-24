package travelfeeldog.aggregate.community.feed.domain.application.usecase;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travelfeeldog.aggregate.community.feed.domain.application.service.FeedWriteService;
import travelfeeldog.aggregate.community.feed.domain.application.service.FeedTagService;
import travelfeeldog.aggregate.member.domain.application.service.MemberService;
import travelfeeldog.aggregate.community.dto.FeedDtos.FeedPostRequestDto;
import travelfeeldog.aggregate.community.dto.FeedDtos.FeedStaticResponseDto;
import travelfeeldog.aggregate.community.feed.domain.model.Feed;
import travelfeeldog.aggregate.community.feed.domain.model.Tag;
import travelfeeldog.aggregate.member.domain.model.Member;

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
