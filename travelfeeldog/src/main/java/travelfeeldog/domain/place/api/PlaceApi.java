package travelfeeldog.domain.place.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import travelfeeldog.domain.place.service.PlaceService;

@RestController
@RequestMapping("/place")
@RequiredArgsConstructor
public class PlaceApi {

    private final PlaceService placeService;



}
