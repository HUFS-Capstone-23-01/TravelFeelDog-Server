package travelfeeldog.domain.review.reviewkeyword.model;

import static javax.persistence.FetchType.LAZY;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import travelfeeldog.domain.review.keyword.model.BadKeyWord;
import travelfeeldog.domain.review.review.model.Review;


@Getter @Setter
@Entity
public class ReviewBadKeyWord{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="review_bad_key_word_id")
    private Long id;

    @ManyToOne(fetch = LAZY,cascade= CascadeType.PERSIST)
    @JoinColumn(name="review_id")
    private Review review ;

    @ManyToOne(fetch = LAZY,cascade= CascadeType.PERSIST)
    @JoinColumn(name="bad_key_word_id")
    private BadKeyWord badKeyWord;

    public ReviewBadKeyWord(Review review ,BadKeyWord bad){
        this.review=review;
        this.badKeyWord = bad;
    }

    public ReviewBadKeyWord() {

    }
}
