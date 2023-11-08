package travelfeeldog.review.keyword.domain.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.Getter;

@Getter
public class BadKeyWords {
    private final List<BadKeyWord> badKeyWords;

    public BadKeyWords(List<BadKeyWord> badKeyWords) {
        this.badKeyWords = badKeyWords;
    }

    public List<ReviewKeyWordInfo> getReviewTotalInfo(final List<Long> keyWordIds) {
        List<ReviewKeyWordInfo> arr = new ArrayList<>();
        Map<BadKeyWord, Integer> map = getNumberOfKeyWordsUse(keyWordIds);
        for (Entry<BadKeyWord, Integer> item : map.entrySet()) {
            arr.add(new ReviewKeyWordInfo(item.getKey(), item.getValue()));
        }
        return arr;
    }

    public Map<BadKeyWord, Integer> getNumberOfKeyWordsUse(final List<Long> keyWordIds) {
        Map<BadKeyWord, Integer> map = new HashMap<>();
        for (Long keyWordId : keyWordIds) {
            for (BadKeyWord badKeyWord : badKeyWords) {
                if (badKeyWord.isSameId(keyWordId)) {
                    map.put(badKeyWord, map.getOrDefault(badKeyWord, 0) + 1);
                }
            }
        }
        return map;
    }
}
