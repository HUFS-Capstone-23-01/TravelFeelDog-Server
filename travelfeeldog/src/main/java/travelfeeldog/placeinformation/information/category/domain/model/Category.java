package travelfeeldog.placeinformation.information.category.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import travelfeeldog.placeinformation.dto.PlaceDtos.PlaceDetailDto;
import travelfeeldog.review.keyword.domain.model.BadKeyWord;
import travelfeeldog.review.keyword.domain.model.GoodKeyWord;
import travelfeeldog.placeinformation.place.domain.model.Place;

@Getter
@Entity
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST)
    private List<Place> places = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST)
    private List<GoodKeyWord> goodKeyWords = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST)
    private List<BadKeyWord> badKeyWords = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }

    public List<PlaceDetailDto> getPlaceDetails() {
        return this.getPlaces()
                .stream()
                .map(PlaceDetailDto::new).collect(Collectors.toList());
    }

}
