package travelfeeldog.factory;

import static org.jeasy.random.FieldPredicates.named;
import java.time.LocalDate;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.range.LongRangeRandomizer;

public class FeedFixtureFactory {

    public static EasyRandom get(Long memberId, LocalDate start, LocalDate end) {
        EasyRandomParameters parameter = getEasyRandomParameters();
        parameter.dateRange(start,end);

        return new EasyRandom(parameter);
    }

    private static EasyRandomParameters getEasyRandomParameters() {
        return new EasyRandomParameters()
                .excludeField(named("id")) // exclude member_id
                .stringLengthRange(1, 100)
                .randomize(Long.class, new LongRangeRandomizer(1L, 100000L));
    }

}
