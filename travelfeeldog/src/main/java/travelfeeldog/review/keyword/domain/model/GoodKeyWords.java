package travelfeeldog.review.keyword.domain.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.Getter;

@Getter
public class GoodKeyWords {

    private final List<GoodKeyWord> goodKeyWords;

    public GoodKeyWords(List<GoodKeyWord> goodKeyWords) {
        this.goodKeyWords = goodKeyWords;
    }

    public List<ReviewKeyWordInfo> getReviewTotalInfo(final List<Long> keyWordIds) {
        List<ReviewKeyWordInfo> arr = new ArrayList<>();
        Map<GoodKeyWord, Integer> map = getNumberOfKeyWordsUse(keyWordIds);
        for (Entry<GoodKeyWord, Integer> item : map.entrySet()) {
            arr.add(new ReviewKeyWordInfo(item.getKey(), item.getValue()));
        }
        return arr;
    }

    public Map<GoodKeyWord, Integer> getNumberOfKeyWordsUse(final List<Long> keyWordIds) {
        Map<GoodKeyWord, Integer> map = new HashMap<>();
        for (Long keyWordId : keyWordIds) {
            for (GoodKeyWord goodKeyWord : goodKeyWords) {
                if (goodKeyWord.isSameId(keyWordId)) {
                    map.put(goodKeyWord, map.getOrDefault(goodKeyWord, 0) + 1);
                }
            }
        }
        return map;
    }
}
