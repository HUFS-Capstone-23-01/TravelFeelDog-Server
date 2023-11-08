package travelfeeldog.review.reviewkeyword.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.placeinformation.place.domain.model.Place;
import travelfeeldog.placeinformation.place.service.PlaceService;
import travelfeeldog.review.keyword.domain.model.BadKeyWords;
import travelfeeldog.review.keyword.domain.model.GoodKeyWords;
import travelfeeldog.review.keyword.domain.model.ReviewKeyWordInfo;
import travelfeeldog.review.keyword.service.KeyWordService;
import travelfeeldog.review.reviewpost.domain.model.Review;
import travelfeeldog.review.reviewkeyword.domain.model.ReviewBadKeyWord;
import travelfeeldog.review.reviewkeyword.domain.model.ReviewGoodKeyWord;
import travelfeeldog.review.reviewkeyword.domain.ReviewBadKeyWordRepository;
import travelfeeldog.review.reviewkeyword.domain.ReviewGoodKeyWordRepository;
import travelfeeldog.review.dto.ReviewDtos.ReviewPostRequestDto;
import travelfeeldog.review.dto.ReviewKeyWordResponseByCategoryDto;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ReviewKeyWordService {
    private final ReviewBadKeyWordRepository reviewBadKeyWordRepository;
    private final ReviewGoodKeyWordRepository reviewGoodKeyWordRepository;
    private final KeyWordService keyWordService;
    private final PlaceService placeService;

    @Transactional
    public void saveReviewKeyWords(List<Long> badKeyWordIds, List<Long> goodKeyWordIds, Review review) {
        badKeyWordIds.forEach(id -> reviewBadKeyWordRepository.save(
                new ReviewBadKeyWord(review, keyWordService.getBadKeyWordById(id))));
        goodKeyWordIds.forEach(id -> reviewGoodKeyWordRepository.save(
                new ReviewGoodKeyWord(review, keyWordService.getGoodKeyWordById(id))));
    }

    public void saveReviewKeyWords(ReviewPostRequestDto request, Review review) {
        saveReviewKeyWords(request.getBadKeyWordIds(), request.getGoodKeyWordIds(), review);
    }

    public ReviewKeyWordResponseByCategoryDto getGoodOrBadKeyWordsByPlace(Long placeId, String givenKeyWord) {
        Place place = placeService.getPlaceById(placeId);
        List<Long> reviewIds = place.getReviews().stream().map(Review::getId).toList();
        Long categoryId = place.getCategory().getId();
        if (givenKeyWord.equals("GOOD")) {
            return getGoodReviewResponse(reviewIds, categoryId);
        }
        return getBadReviewResponse(reviewIds, categoryId);
    }

    public ReviewKeyWordResponseByCategoryDto getGoodReviewResponse(List<Long> reviewIds, Long categoryId) {
        GoodKeyWords goodKeyWords = keyWordService.getGoodKeyWordByCategoryId(categoryId);
        List<List<Long>> keyWordsIdsByBadKeyWordsIdsFromReviews = reviewIds.stream()
                .map(reviewBadKeyWordRepository::getAllBadKeyWordIds).toList();
        List<ReviewKeyWordInfo> keyWords = goodKeyWords.getReviewTotalInfo(
                keyWordsIdsByBadKeyWordsIdsFromReviews.stream().flatMap(List::stream).toList());
        return new ReviewKeyWordResponseByCategoryDto(keyWords);
    }

    public ReviewKeyWordResponseByCategoryDto getBadReviewResponse(List<Long> reviewIds, Long categoryId) {
        BadKeyWords badKeyWords = keyWordService.getBadKeyWordByCategoryId(categoryId);
        List<List<Long>> keyWordsIdsByGoodKeyWordsIdsFromReviews = reviewIds.stream()
                .map(reviewGoodKeyWordRepository::getAllGoodKeyWordIds).toList();
        List<ReviewKeyWordInfo> keyWords = badKeyWords.getReviewTotalInfo(
                keyWordsIdsByGoodKeyWordsIdsFromReviews.stream().flatMap(List::stream).toList());
        return new ReviewKeyWordResponseByCategoryDto(keyWords);
    }
}






















