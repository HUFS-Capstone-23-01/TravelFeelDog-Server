package travelfeeldog.review.domain.keyword.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class BaseKeyWordEntity {
    protected String keyWordName;

    protected void setKeyWordName(final String keyWordName) {
        this.keyWordName = keyWordName;
    }
}
