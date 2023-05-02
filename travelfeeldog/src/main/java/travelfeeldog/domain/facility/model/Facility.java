package travelfeeldog.domain.facility.model;

import static javax.persistence.FetchType.LAZY;

import java.util.ArrayList;
import java.util.List;
import travelfeeldog.domain.placefacility.model.PlaceFacility;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="facility_id")
    private Long id;

    private String name;

    @OneToMany(fetch = LAZY,cascade= CascadeType.PERSIST)
    private List<PlaceFacility> placeFacilities =new ArrayList<>();

}
