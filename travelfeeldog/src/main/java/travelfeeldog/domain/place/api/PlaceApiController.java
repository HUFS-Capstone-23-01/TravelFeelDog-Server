package travelfeeldog.domain.place.api;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import travelfeeldog.domain.place.dto.PlaceDtos.PlaceDetailDto;
import travelfeeldog.domain.place.dto.PlaceDtos.PlacePostRequestDto;
import travelfeeldog.domain.place.dto.PlaceDtos.PlaceResponseDetailDto;
import travelfeeldog.domain.place.model.Place;
import travelfeeldog.domain.place.service.PlaceService;
import travelfeeldog.global.common.dto.ApiResponse;
import travelfeeldog.infra.aws.s3.service.AwsS3ImageService;

@RestController
@RequestMapping("/place")
@RequiredArgsConstructor
public class PlaceApiController {

    private final PlaceService placeService;
    private final AwsS3ImageService awsS3ImageService;
    @PostMapping(produces = "application/json;charset=UTF-8")
    public ApiResponse<PlaceDetailDto> addNewPlace(@RequestBody PlacePostRequestDto request) {
        return ApiResponse.success(placeService.addNewPlace(request));
    }
    @PutMapping(value = "/image",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse updatePlaceImageUrl(@RequestParam("placeId") Long placeId,
                                           @RequestParam("file") MultipartFile file) throws IOException {
        String imageUrl = awsS3ImageService.uploadImageOnly(file,"place");
        Place place = placeService.changeImageUrl(placeId,imageUrl);
        return  ApiResponse.success(new PlaceDetailDto(place));
    }
    @GetMapping(value = "/all", produces = "application/json;charset=UTF-8")
    public ApiResponse<List<PlaceDetailDto>> getAllPlaces() {
        List<PlaceDetailDto> placeDetailResponse = placeService.getAllPlaces().stream()
                .map(PlaceDetailDto::new)
                .collect(Collectors.toList());
        return ApiResponse.success(placeDetailResponse);
    }
    @GetMapping(value = "/{placeId}", produces = "application/json;charset=UTF-8")
    public ApiResponse<PlaceResponseDetailDto> getPlaceDetailInfo(@PathVariable Long placeId,@RequestHeader("Authorization") String token) {
        PlaceResponseDetailDto placeDetailDto =placeService.getPlaceDetailById(placeId);
        return ApiResponse.success(placeDetailDto);
    }
}