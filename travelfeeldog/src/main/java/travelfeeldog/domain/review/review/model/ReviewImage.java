package travelfeeldog.domain.review.review.model;

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

@Getter @Setter
@Entity
public class ReviewImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="review_image_id")
    private Long id;
    private String imageUrl;
    @ManyToOne(fetch = LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name="review_id")
    private Review review;
    public ReviewImage() {

    }
    public  ReviewImage(Review review,String imageUrl){
        this.review=review;
        this.imageUrl = imageUrl;
    }

    public void updateImage(String reviewImage) {
        this.imageUrl = reviewImage;
    }
}
