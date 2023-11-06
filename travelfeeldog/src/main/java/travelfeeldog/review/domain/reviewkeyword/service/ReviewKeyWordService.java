package travelfeeldog.review.domain.reviewkeyword.service;

import java.util.List;

import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import travelfeeldog.member.domain.application.service.MemberService;
import travelfeeldog.place.domain.place.model.Place;
import travelfeeldog.place.domain.place.service.PlaceService;
import travelfeeldog.review.domain.reviewkeyword.repository.ReviewBadKeyWordRepository;
import travelfeeldog.review.dto.KeyWordDtos.KeyWordResponseByCategoryDto;
import travelfeeldog.review.domain.keyword.service.KeyWordService;
import travelfeeldog.review.dto.ReviewDtos.ReviewPostRequestDto;
import travelfeeldog.review.domain.review.model.Review;
import travelfeeldog.review.dto.ReviewKeyWordDtos.ReviewKeyWordResponseByCategoryDto;
import travelfeeldog.review.dto.ReviewKeyWordDtos.ReviewKeyWordResponseDto;
import travelfeeldog.review.domain.reviewkeyword.model.ReviewBadKeyWord;
import travelfeeldog.review.domain.reviewkeyword.model.ReviewGoodKeyWord;
import travelfeeldog.review.domain.reviewkeyword.repository.ReviewGoodKeyWordRepository;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ReviewKeyWordService {
    private final ReviewBadKeyWordRepository reviewBadKeyWordRepository;
    private final ReviewGoodKeyWordRepository reviewGoodKeyWordRepository;
    private final KeyWordService keyWordService;
    private final PlaceService placeService;
    private final MemberService memberService;

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

    public ReviewKeyWordResponseByCategoryDto getGoodOrBadKeyWordsByPlace(Long placeId, String givenKeyWord,
                                                                          String token) {
        memberService.findByToken(token);
        Place place = placeService.getPlaceById(placeId);
        List<Long> reviewIds = place.getReviews().stream().map(Review::getId).toList();
        KeyWordResponseByCategoryDto allKeyWords = keyWordService.getAllKeyWordsByCategory(place.getCategory().getId());
        if (givenKeyWord.equals("GOOD")) {
            List<ReviewKeyWordResponseDto> keyWords = setGood(allKeyWords, reviewIds);
            return new ReviewKeyWordResponseByCategoryDto(keyWords);
        }
        List<ReviewKeyWordResponseDto> keyWords = setBad(allKeyWords, reviewIds);
        return new ReviewKeyWordResponseByCategoryDto(keyWords);
    }

    private List<ReviewKeyWordResponseDto> setGood(KeyWordResponseByCategoryDto allKeyWords, List<Long> reviewIds) {
        List<ReviewKeyWordResponseDto> keyWords = allKeyWords.getGoodKeyWords()
                .stream()
                .map(ReviewKeyWordResponseDto::new)
                .collect(Collectors.toList());
        reviewIds.forEach(reviewId -> update(reviewGoodKeyWordRepository.getAllGoodKeyWordIds(reviewId), keyWords));
        return keyWords;
    }

    private List<ReviewKeyWordResponseDto> setBad(KeyWordResponseByCategoryDto allKeyWords, List<Long> reviewIds) {
        List<ReviewKeyWordResponseDto> keyWords = allKeyWords.getBadKeyWords()
                .stream()
                .map(ReviewKeyWordResponseDto::new)
                .collect(Collectors.toList());
        reviewIds.forEach(reviewId -> update(reviewBadKeyWordRepository.getAllBadKeyWordIds(reviewId), keyWords));
        return keyWords;
    }

    private void update(List<Long> keyWordIds, List<ReviewKeyWordResponseDto> keyWords) {
        for (Long item : keyWordIds) {
            for (ReviewKeyWordResponseDto dto : keyWords) {
                if (Objects.equals(dto.getKeyWordId(), item)) {
                    int num = dto.getKeyWordCount();
                    num += 1;
                    dto.setKeyWordCount(num);
                }
            }

        }
    }
}






















