package travelfeeldog.review.keyword.domain.model;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import travelfeeldog.placeinformation.information.category.domain.model.Category;

@Getter
@Entity
public class BadKeyWord extends BaseKeyWordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bad_key_word_id")
    private Long id;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category;

    public BadKeyWord(Category category, String keyWord) {
        super();
        this.category = category;
        this.setKeyWordName(keyWord);
    }

    public BadKeyWord() {

    }

    public boolean isSameId(Long keyWordId) {
        return this.id.equals(keyWordId);
    }
}
