package travelfeeldog.domain.review.reviewkeyword.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import travelfeeldog.domain.place.place.model.Place;
import travelfeeldog.domain.place.place.service.PlaceService;

import travelfeeldog.domain.review.keyword.dto.KeyWordDtos.KeyWordResponseByCategoryDto;

import travelfeeldog.domain.review.keyword.service.KeyWordService;
import travelfeeldog.domain.review.review.dto.ReviewDtos.ReviewPostRequestDto;
import travelfeeldog.domain.review.review.model.Review;
import travelfeeldog.domain.review.reviewkeyword.dao.ReviewGoodKeyWordRepository;

import travelfeeldog.domain.review.reviewkeyword.dto.ReviewKeyWordDtos.ReviewKeyWordResponseDto;
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
    public void getGoodOrBadKeyWordsByPlace(Long placeId,String keyWord) { // need split
        List<ReviewKeyWordResponseDto> keyWords = null;
        Place place = placeService.getPlaceById(placeId);
        List<Long> reviews = place.getReviews().stream().map(Review::getId).toList();
        KeyWordResponseByCategoryDto allKeyWords = keyWordService.getAllKeyWordsByCategory(place.getCategory().getId());
        if(keyWord.equals("GOOD")) {
            keyWords = allKeyWords.getGoodKeyWords().stream().map(
                    ReviewKeyWordResponseDto::new).toList();
            for(Long reviewId :reviews){
                List<Long> goodKeyWordIds = reviewGoodKeyWordRepository.getAllGoodKeyWordIds(reviewId);
            }
        }
        if(keyWord.equals("BAD")) {
            keyWords = allKeyWords.getBadKeyWords().stream().map(ReviewKeyWordResponseDto::new).toList();
            for(Long reviewId :reviews){
                List<Long> goodKeyWordIds = reviewGoodKeyWordRepository.getAllGoodKeyWordIds(reviewId);
            }
        }

    }
}






















