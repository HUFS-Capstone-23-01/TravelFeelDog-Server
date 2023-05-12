package travelfeeldog.domain.review.review.model;

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

@Getter @Setter
@Entity
public class ReviewImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="review_image_id")
    private Long id;
    private String imageUrl;
    @ManyToOne(fetch = LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="review_id")
    private Review review;
    public ReviewImage() {

    }
    public  ReviewImage(Review review,String imageUrl){
        this.review=review;
        this.imageUrl = imageUrl;
    }
}
