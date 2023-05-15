package travelfeeldog.domain.review.reviewkeyword.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.domain.place.category.model.Category;
import travelfeeldog.domain.place.category.service.CategoryService;
import travelfeeldog.domain.place.place.model.Place;
import travelfeeldog.domain.place.place.service.PlaceService;
import travelfeeldog.domain.review.keyword.dto.KeyWordDtos.BadKeyWordResponseDto;
import travelfeeldog.domain.review.keyword.dto.KeyWordDtos.GoodKeyWordResponseDto;
import travelfeeldog.domain.review.keyword.dto.KeyWordDtos.KeyWordResponseByCategoryDto;
import travelfeeldog.domain.review.keyword.model.BadKeyWord;
import travelfeeldog.domain.review.keyword.model.GoodKeyWord;
import travelfeeldog.domain.review.keyword.service.KeyWordService;
import travelfeeldog.domain.review.review.dto.ReviewDtos.ReviewPostRequestDto;
import travelfeeldog.domain.review.review.model.Review;
import travelfeeldog.domain.review.reviewkeyword.dao.ReviewGoodKeyWordRepository;
import travelfeeldog.domain.review.reviewkeyword.dto.ReviewKeyWordDtos.ReviewBadKeyWordResponseDto;
import travelfeeldog.domain.review.reviewkeyword.dto.ReviewKeyWordDtos.ReviewGoodKeyWordResponseDto;
import travelfeeldog.domain.review.reviewkeyword.model.ReviewBadKeyWord;
import travelfeeldog.domain.review.reviewkeyword.dao.ReviewBadKeyWordRepository;
import travelfeeldog.domain.review.reviewkeyword.model.ReviewGoodKeyWord;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ReviewKeyWordService {
    private final ReviewBadKeyWordRepository reviewBadKeyWordRepository;
    private final ReviewGoodKeyWordRepository reviewGoodKeyWordRepository;
    private final KeyWordService keyWordService;
    private final PlaceService placeService;
    @Transactional
    public void saveReviewKeyWords(List<Long> badKeyWordIds , List<Long> goodKeyWordIds, Review review){
        for(Long id : badKeyWordIds){
            reviewBadKeyWordRepository.save(new ReviewBadKeyWord(review,keyWordService.getBadKeyWordById(id)));
        }
        for(Long id : goodKeyWordIds){
            reviewGoodKeyWordRepository.save((new ReviewGoodKeyWord(review,keyWordService.getGoodKeyWordById(id))));
        }
    }
    public void saveReviewKeyWords(ReviewPostRequestDto request,Review review){
        saveReviewKeyWords(request.getBadKeyWordIds(),request.getGoodKeyWordIds(),review);
    }
    public void getGoodKeyWordsByPlace(Long placeId){ // need split
        Place place = placeService.getPlaceById(placeId);
        KeyWordResponseByCategoryDto allKeyWords = keyWordService.getAllKeyWordsByCategory(place.getCategory().getId());

        List<ReviewGoodKeyWordResponseDto> goodKeyWords = allKeyWords.getGoodKeyWords().stream().map(ReviewGoodKeyWordResponseDto::new).toList();
        List<ReviewBadKeyWordResponseDto> badKeyWords = allKeyWords.getBadKeyWords().stream().map(ReviewBadKeyWordResponseDto::new).toList();

        List<Long> reviews = place.getReviews().stream().map(Review::getId).toList();
        // need mapped by id count
        for(Long reviewId :reviews){
            List<Long> goodKeyWordIds = reviewGoodKeyWordRepository.getAllGoodKeyWordIds(reviewId);
            List<Long> badKeyWordIds = reviewBadKeyWordRepository.getAllBadKeyWordIds(reviewId);
        }

    }
}






















