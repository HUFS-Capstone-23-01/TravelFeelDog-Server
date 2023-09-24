package travelfeeldog.review.domain.reviewkeyword.model;

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
import lombok.Setter;
import travelfeeldog.review.domain.keyword.model.GoodKeyWord;
import travelfeeldog.review.domain.review.model.Review;

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
