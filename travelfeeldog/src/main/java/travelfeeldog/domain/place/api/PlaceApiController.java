package travelfeeldog.domain.place.api;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import travelfeeldog.domain.place.dto.PlaceDtos.PlaceDetailDto;
import travelfeeldog.domain.place.dto.PlaceDtos.PlacePostRequestDto;
import travelfeeldog.domain.place.model.Place;
import travelfeeldog.domain.place.service.PlaceService;
import travelfeeldog.global.common.dto.ApiResponse;

@RestController
@RequestMapping("/place")
@RequiredArgsConstructor
public class PlaceApiController {

    private final PlaceService placeService;

    @PostMapping(produces = "application/json;charset=UTF-8")
    public ApiResponse<Place> addNewPlace(@RequestBody PlacePostRequestDto request) {
        return ApiResponse.success(placeService.addNewPlace(request));
    }

    @GetMapping(value = "/all", produces = "application/json;charset=UTF-8")
    public ApiResponse<List<PlaceDetailDto>> getAllPlaces() {
        List<PlaceDetailDto> placeDetailResponse = placeService.getAllPlaces().stream()
                .map(PlaceDetailDto::new)
                .collect(Collectors.toList());
        return ApiResponse.success(placeDetailResponse);
    }

    @GetMapping(value = "/{placeId}", produces = "application/json;charset=UTF-8")
    public ApiResponse<PlaceDetailDto> getPlaceDetailInfo(@PathVariable Long placeId) {
        PlaceDetailDto placeDetailDto = new PlaceDetailDto(placeService.getOneByPlaceId(placeId));
        return ApiResponse.success(placeDetailDto);
    }
}