package travelfeeldog.domain.place.facility.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
@Entity
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="facility_id")
    private Long id;
    private String name;
    public Facility(String name){
        this.name = name;
    }

    public void updateName(String name) {
        this.name = name;
    }
}
