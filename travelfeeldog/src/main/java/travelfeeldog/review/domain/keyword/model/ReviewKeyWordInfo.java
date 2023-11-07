package travelfeeldog.review.domain.keyword.model;

public final class ReviewKeyWordInfo {
    private final Long keyWordId;
    private final String keyWordName;
    private final Integer keyWordCount;

    public ReviewKeyWordInfo(final GoodKeyWord goodKeyWord, final Integer count) {
        this.keyWordId = goodKeyWord.getId();
        this.keyWordName = goodKeyWord.getKeyWordName();
        this.keyWordCount = count;
    }

    public ReviewKeyWordInfo(final BadKeyWord badKeyWord, final Integer count) {
        this.keyWordId = badKeyWord.getId();
        this.keyWordName = badKeyWord.getKeyWordName();
        this.keyWordCount = count;
    }
}
