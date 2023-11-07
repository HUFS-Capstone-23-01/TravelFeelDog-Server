package travelfeeldog.review.domain.keyword.model;

public class ReviewKeyWordInfo {

    private final Long keyWordId;
    private final String keyWordName;
    private final Integer keyWordCount;

    public ReviewKeyWordInfo(GoodKeyWord goodKeyWord, Integer count) {
        this.keyWordId = goodKeyWord.getId();
        this.keyWordName = goodKeyWord.getKeyWordName();
        this.keyWordCount = count;
    }

    public ReviewKeyWordInfo(BadKeyWord badKeyWord, Integer count) {
        this.keyWordId = badKeyWord.getId();
        this.keyWordName = badKeyWord.getKeyWordName();
        this.keyWordCount = count;
    }
}
