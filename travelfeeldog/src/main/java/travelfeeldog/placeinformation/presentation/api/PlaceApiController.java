package travelfeeldog.placeinformation.presentation.api;

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
import travelfeeldog.placeinformation.place.domain.model.KeyWord;
import travelfeeldog.placeinformation.dto.PlaceDtos.PlaceDetailDto;
import travelfeeldog.placeinformation.dto.PlaceDtos.PlacePostRequestDto;
import travelfeeldog.placeinformation.dto.PlaceDtos.PlaceResponseDetailDto;
import travelfeeldog.placeinformation.dto.PlaceDtos.PlaceResponseRecommendDetailDto;
import travelfeeldog.placeinformation.dto.PlaceDtos.PlaceReviewCountSortResponseDto;
import travelfeeldog.placeinformation.dto.PlaceDtos.PlaceSearchResponseDto;
import travelfeeldog.placeinformation.place.domain.model.Place;
import travelfeeldog.placeinformation.place.service.PlaceGptSearchService;
import travelfeeldog.placeinformation.place.service.PlaceService;
import travelfeeldog.global.common.dto.ApiResponse;
import travelfeeldog.global.file.domain.application.ImageFileService;

@RestController
@RequestMapping("/place")
@RequiredArgsConstructor
public class PlaceApiController {

    private final PlaceService placeService;
    private final PlaceGptSearchService placeGptSearchService;
    private final ImageFileService imageFileService;

    @PostMapping(produces = "application/json;charset=UTF-8")
    public ApiResponse<PlaceDetailDto> addNewPlace(@RequestBody PlacePostRequestDto request) {
        return ApiResponse.success(placeService.addNewPlace(request));
    }

    @PutMapping(value = "/image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse updatePlaceImageUrl(@RequestParam("placeId") Long placeId,
                                           @RequestParam("file") MultipartFile file) throws IOException {
        String imageUrl = imageFileService.uploadImageFile(file, "place");
        Place place = placeService.changeImageUrl(placeId, imageUrl);
        return ApiResponse.success(new PlaceDetailDto(place));
    }

    @GetMapping(value = "/{placeId}", produces = "application/json;charset=UTF-8")
    public ApiResponse<PlaceResponseDetailDto> getPlaceDetailInfo(@PathVariable Long placeId,
                                                                  @RequestHeader("Authorization") String token) {
        PlaceResponseDetailDto placeDetailDto = placeService.getPlaceDetailById(placeId);
        return ApiResponse.success(placeDetailDto);
    }

    @GetMapping(value = "/all", produces = "application/json;charset=UTF-8")
    public ApiResponse<List<PlaceDetailDto>> getAllPlaces() {
        List<PlaceDetailDto> placeDetailResponse = placeService.getAllPlaces().stream()
                .map(PlaceDetailDto::new)
                .collect(Collectors.toList());
        return ApiResponse.success(placeDetailResponse);
    }

    @GetMapping(value = "/recommend", produces = "application/json;charset=UTF-8")
    public ApiResponse<List<PlaceResponseRecommendDetailDto>> getRecommendPlaces(
            @RequestParam("categoryName") String categoryName,
            @RequestParam("locationName") String locationName,
            @RequestHeader("Authorization") String token) {
        return ApiResponse.success(placeService.getResponseRecommend(categoryName, locationName));
    }

    @GetMapping(value = "/most/review", produces = "application/json;charset=UTF-8")
    public ApiResponse<List<PlaceReviewCountSortResponseDto>> getMostReviewPlace(
            @RequestParam("locationName") String locationName,
            @RequestHeader("Authorization") String token) {
        return ApiResponse.success(placeService.getMostReviewPlace(locationName));
    }

    @GetMapping(value = "/search", produces = "application/json;charset=UTF-8")
    public ApiResponse<List<PlaceSearchResponseDto>> getPlacesBySearch(
            @RequestParam("categoryName") String categoryName,
            @RequestParam("locationName") String locationName,
            @RequestParam("keyWord") String keyWord,
            @RequestHeader("Authorization") String token) {
        return ApiResponse.success(
                placeService.getResponseSearch(categoryName, locationName, new KeyWord(keyWord)));
    }

    @GetMapping(value = "/search/gpt", produces = "application/json;charset=UTF-8")
    public ApiResponse<String> getPlaceSearchByGpt(@RequestParam("question") String question) {
        return ApiResponse.success(placeGptSearchService.answerText(question, 1.0f, 3000));
    }
}
