package travelfeeldog.domain.review.model;

import static javax.persistence.FetchType.LAZY;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import travelfeeldog.domain.member.model.Member;
import travelfeeldog.domain.place.model.Place;
import travelfeeldog.domain.reviewkeyword.model.ReviewBadKeyWord;
import travelfeeldog.domain.reviewkeyword.model.ReviewGoodKeyWord;
import travelfeeldog.global.common.model.BaseTimeEntity;

@Setter
@Getter
@Entity
public class Review extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="review_id")
    private Long id;

    @ColumnDefault("0")
    @Column(name="small_dog_number")
    private int smallDogNumber;
    @ColumnDefault("0")
    @Column(name="medium_dog_number")
    private int mediumDogNumber;
    @ColumnDefault("0")
    @Column(name="larger_dog_number")
    private int largeDogNumber;

    private String additionalScript;
    @ManyToOne(fetch = LAZY,cascade= CascadeType.PERSIST)
    @JoinColumn(name="place_id")
    private Place place;
    @ManyToOne(fetch = LAZY,cascade= CascadeType.PERSIST)
    @JoinColumn(name="member_id")
    private Member member;
    @OneToMany (mappedBy = "review",cascade = CascadeType.PERSIST)
    private List<ReviewGoodKeyWord> reviewGoodKeyWords = new ArrayList<>();
    @OneToMany (mappedBy = "review",cascade = CascadeType.PERSIST)
    private List<ReviewBadKeyWord> reviewBadKeyWords = new ArrayList<>();
}
