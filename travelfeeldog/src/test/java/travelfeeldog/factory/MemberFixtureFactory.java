package travelfeeldog.factory;

import static org.jeasy.random.FieldPredicates.named;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.range.LongRangeRandomizer;
import org.jeasy.random.randomizers.text.StringRandomizer;
import travelfeeldog.aggregate.member.domain.model.Member;
import travelfeeldog.aggregate.member.dto.MemberDto;

public class MemberFixtureFactory {

    public static Member create(Long seed) {
        var parameter = new EasyRandomParameters()
                .seed(seed)
                .excludeField(named("id"))
                .stringLengthRange(1, 10)
                .randomize(Long.class, new LongRangeRandomizer(1L, 100L));
        return new EasyRandom(parameter).nextObject(Member.class);
    }

    public static MemberDto createDto() {
        return new MemberDto(
                new LongRangeRandomizer(1L, 100L).getRandomValue(),
                new StringRandomizer(10).getRandomValue(),
                new StringRandomizer(10).getRandomValue()
        );
    }
}
