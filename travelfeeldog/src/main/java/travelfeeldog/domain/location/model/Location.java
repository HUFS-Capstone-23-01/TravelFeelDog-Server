package travelfeeldog.domain.location.model;

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
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import travelfeeldog.domain.place.model.Place;

@Setter
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

}
