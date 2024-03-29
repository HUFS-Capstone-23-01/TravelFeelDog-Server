package travelfeeldog.review.reviewkeyword.domain.model;

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
import travelfeeldog.review.keyword.domain.model.BadKeyWord;
import travelfeeldog.review.reviewpost.domain.model.Review;


@Getter
@Entity
public class ReviewBadKeyWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_bad_key_word_id")
    private Long id;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bad_key_word_id")
    private BadKeyWord badKeyWord;

    public ReviewBadKeyWord(Review review, BadKeyWord bad) {
        this.review = review;
        this.badKeyWord = bad;
    }

    public ReviewBadKeyWord() {

    }
}
