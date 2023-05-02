package travelfeeldog.domain.place.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.domain.place.dao.PlaceRepository;
import travelfeeldog.domain.place.model.Place;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    @Transactional
    public Place savePlace(){
        return placeRepository.save(new Place());
    }

}
