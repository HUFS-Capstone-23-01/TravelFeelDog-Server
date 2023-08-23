package travelfeeldog.domain.feed;

import java.time.LocalDate;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import travelfeeldog.IntegrationTest;
import travelfeeldog.domain.community.feed.dao.FeedRepository;
import travelfeeldog.domain.community.feed.service.FeedReadService;
import travelfeeldog.factory.FeedFixtureFactory;

@IntegrationTest
public class FeedReadServiceTest {
    @Autowired
    private FeedReadService feedReadService;
    @Autowired
    private FeedRepository feedRepository;

    @DisplayName("일별 작성 게시물 갯수를 반환한다")
    @Test
    public void testGetDailyPostCount() {
        var memberId = 1L;
        LocalDate _01월_01일 = LocalDate.of(2022, 1, 1);
        LocalDate endDate = _01월_01일.plusDays(30);

        var posts = IntStream.range(0, 3)
                .mapToObj(i -> FeedFixtureFactory.create(memberId, _01월_01일))
                .toList();
        postRepository.bulkInsert(posts);

        var request = new DailyPostCountRequest(memberId, _01월_01일, endDate);
        var result = postReadService.getDailyPostCounts(request);

        assertEquals(1, result.size());
        assertEquals(3, result.get(0).postCount());
        assertEquals(_01월_01일, result.get(0).date());
    }

    @DisplayName("페이징쿼리")
    @Test
    public void testPage() {
        PageRequest pageRequest = PageRequest.of(0, 10,
                Sort.by("createdDate").descending()
                        .and(Sort.by("id").descending())
        );
        var result = feedReadService.getFeed(10L, pageRequest);

        System.out.println(result.stream().map(it -> it.getCreatedDate().toString() + " " + it.getId()).toList());
    }

    @DisplayName("커서 페이징")
    @Test
    public void testCursorPage() {
        var cursor = new CursorRequest(24L, 2);
        var result = feedReadService.getFeeds(-1L, cursor);

        System.out.println("Next Cusor: " + result.nextCursorRequest());
        System.out.println(result.body()
                .stream()
                .map(it -> it.getCreatedDate().toString() + " " + it.getId() + " "+ it.getContents())
                .toList()
        );
    }
}
