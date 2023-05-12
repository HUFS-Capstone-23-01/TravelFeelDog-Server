package travelfeeldog.domain.review.keyword.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@MappedSuperclass
public abstract class BaseKeyWordEntity {
    private String keyWordName;
}
