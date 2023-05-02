package travelfeeldog.domain.category.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import travelfeeldog.domain.keyword.model.BadKeyWord;
import travelfeeldog.domain.keyword.model.GoodKeyWord;
import travelfeeldog.domain.place.model.Place;

@Setter
@Getter
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category" ,cascade = CascadeType.PERSIST)
    private List<Place> places = new ArrayList<>();

    @OneToMany(mappedBy = "category" ,cascade = CascadeType.PERSIST)
    private List<GoodKeyWord> goodKeyWords = new ArrayList<>();

    @OneToMany(mappedBy = "category" ,cascade = CascadeType.PERSIST)
    private List<BadKeyWord> badKeyWords = new ArrayList<>();


}
