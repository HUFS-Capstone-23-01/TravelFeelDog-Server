package travelfeeldog.domain.keyword.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class BaseKeyWordEntity {
    @Column(name="key_word")
    private String keyWord;
}
