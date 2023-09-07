package travelfeeldog.domain.place.location.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
