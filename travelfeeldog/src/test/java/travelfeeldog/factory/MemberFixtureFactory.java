package travelfeeldog.factory;

import static org.jeasy.random.FieldPredicates.named;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.range.LongRangeRandomizer;
import org.jeasy.random.randomizers.text.StringRandomizer;
import org.jeasy.random.randomizers.time.LocalDateRandomizer;
import travelfeeldog.domain.member.domain.model.Member;
import travelfeeldog.domain.member.dto.MemberDto;

public class MemberFixtureFactory {

//    public static Member create() {
//        var param = new EasyRandomParameters();
//        return new EasyRandom(param).nextObject(Member.class);
//    }

    public static Member create(Long seed) {
        var param = new EasyRandomParameters().seed(seed);
        return new EasyRandom(param).nextObject(Member.class);
    }
    public static Member create() {
        var parameter = new EasyRandomParameters()
                .excludeField(named("id"))
                .stringLengthRange(1, 10)
                .randomize(Long.class, new LongRangeRandomizer(1L, 100L));
        return new EasyRandom(parameter).nextObject(Member.class);
    }

    public static MemberDto createDto() {
        var parameter = new EasyRandomParameters()
                .stringLengthRange(1, 10)
                .randomize(Long.class, new LongRangeRandomizer(1L, 100L));
        return new MemberDto(
                new LongRangeRandomizer(1L, 100L).getRandomValue(),
                new StringRandomizer(10).getRandomValue(),
                new StringRandomizer(10).getRandomValue(),
                new LocalDateRandomizer().getRandomValue()
        );
    }
}
