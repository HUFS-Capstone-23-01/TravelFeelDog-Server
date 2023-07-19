package travelfeeldog.domain.place.category.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import travelfeeldog.domain.place.place.dto.PlaceDtos.PlaceDetailDto;
import travelfeeldog.domain.review.keyword.model.BadKeyWord;
import travelfeeldog.domain.review.keyword.model.GoodKeyWord;
import travelfeeldog.domain.place.place.model.Place;

@Getter
@Entity
@NoArgsConstructor
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
    public Category(String name) {
        this.name = name;
    }

    public List<PlaceDetailDto> getPlaceDetails(){
        return this.getPlaces()
            .stream()
            .map(PlaceDetailDto::new).collect(Collectors.toList());
    }

}
