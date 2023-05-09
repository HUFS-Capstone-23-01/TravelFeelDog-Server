package travelfeeldog.domain.review.model;


public enum RecommendStatus {
    GOOD("GOOD"),
    IDK("IDK"),
    BAD("BAD");

    private String value;

    RecommendStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
