package travelfeeldog.domain.place.location.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;

import lombok.NoArgsConstructor;
import travelfeeldog.domain.place.place.model.Place;

@NoArgsConstructor
@Getter
@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="location_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "location" ,cascade = CascadeType.PERSIST)
    private List<Place> places = new ArrayList<>();

    public Location(String name) {
        this.name = name;
    }
}
