package travelfeeldog.domain.feed;

import java.time.LocalDate;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import travelfeeldog.IntegrationTest;
import travelfeeldog.domain.community.feed.dao.FeedRepository;
import travelfeeldog.domain.community.feed.model.Feed;
import travelfeeldog.factory.FeedFixtureFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@SpringBootTest  // used only for insert data
@IntegrationTest
public class FeedBulkInsertTest {
    private final Logger logger = LoggerFactory.getLogger(FeedBulkInsertTest.class);

    @Autowired
    private FeedRepository feedRepository;
    @Test
    public void bulkInsert() {
        var easyRandom = FeedFixtureFactory.get(
                4L,
                LocalDate.of(1998, 12, 1),
                LocalDate.of(2024, 2, 1)
        );

        var stopWatch = new StopWatch();
        stopWatch.start();

        int _1만 = 10000;
        var posts = IntStream.range(0, _1만*1)
                .parallel()
                .mapToObj(i -> easyRandom.nextObject(Feed.class))
                .toList();

        stopWatch.stop();
        logger.info("객체 생성 시간 : {}" ,stopWatch.getTotalTimeSeconds());

        var queryStopWatch = new StopWatch();
        queryStopWatch.start();

        feedRepository.bulkInsert(posts);

        queryStopWatch.stop();
        logger.info("DB 인서트 시간 : {} " , queryStopWatch.getTotalTimeSeconds());
    }
}
