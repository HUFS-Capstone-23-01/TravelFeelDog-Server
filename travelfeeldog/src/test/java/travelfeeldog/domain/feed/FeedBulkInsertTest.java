//package travelfeeldog.domain.feed;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.stream.IntStream;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.StopWatch;
//import travelfeeldog.community.feed.repository.FeedRepository;
//import travelfeeldog.community.feed.domain.model.Feed;
//import travelfeeldog.factory.FeedFixtureFactory;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import travelfeeldog.IntegrationTest;
//
//
////@SpringBootTest
//@IntegrationTest
//public class FeedBulkInsertTest {
//    private final Logger logger = LoggerFactory.getLogger(FeedBulkInsertTest.class);
//
//    @Autowired
//    private FeedRepository feedRepository;
//    @Test
//    public void bulkInsert() {
//        var easyRandom = FeedFixtureFactory.get(
//                1L,
//                LocalDate.of(1970,12,4),
//                LocalDate.of(2024,2,6)
//
//        );
//        int _10만 = 100000;
//        var stopWatch = new StopWatch();
//        stopWatch.start();
//        List<Feed> posts = IntStream.range(0, _10만*2)
//                .parallel()
//                .mapToObj(i -> easyRandom.nextObject(Feed.class))
//                .toList();
//        posts.forEach(i->{
//            i.getMember().setWriterId(2L);
//            i.syncUpdateTimeToCreatedTime();
//        });
//
//        stopWatch.stop();
//        logger.info("객체 생성 시간 : {}" ,stopWatch.getTotalTimeSeconds());
//
//        var queryStopWatch = new StopWatch();
//        queryStopWatch.start();
//
//        feedRepository.bulkInsert(posts);
//
//        queryStopWatch.stop();
//        logger.info("DB 인서트 시간 : {} " , queryStopWatch.getTotalTimeSeconds());
//    }
//}
