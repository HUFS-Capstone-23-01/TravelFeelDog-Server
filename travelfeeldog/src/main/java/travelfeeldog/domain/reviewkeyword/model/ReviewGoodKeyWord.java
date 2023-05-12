package travelfeeldog.domain.reviewkeyword.model;

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
import travelfeeldog.domain.keyword.model.GoodKeyWord;
import travelfeeldog.domain.review.model.Review;

@Getter @Setter
@Entity
public class ReviewGoodKeyWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="review_good_key_word_id")
    private Long id;

    @ManyToOne(fetch = LAZY,cascade= CascadeType.PERSIST)
    @JoinColumn(name="review_id")
    private Review review ;

    @ManyToOne(fetch = LAZY,cascade= CascadeType.PERSIST)
    @JoinColumn(name="good_key_word_id")
    private GoodKeyWord goodKeyWord;
    public ReviewGoodKeyWord(Review review , GoodKeyWord goodKeyWord){
        this.review=review;
        this.goodKeyWord = goodKeyWord;
    }

    public ReviewGoodKeyWord() {

    }
}
