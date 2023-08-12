package travelfeeldog.domain.review.reviewkeyword.service;

import java.util.List;

import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import travelfeeldog.domain.member.domain.service.MemberService;
import travelfeeldog.domain.place.place.model.Place;
import travelfeeldog.domain.place.place.service.PlaceService;
import travelfeeldog.domain.review.keyword.dto.KeyWordDtos.KeyWordResponseByCategoryDto;
import travelfeeldog.domain.review.review.dto.ReviewDtos.ReviewPostRequestDto;
import travelfeeldog.domain.review.review.model.Review;
import travelfeeldog.domain.review.reviewkeyword.dao.ReviewBadKeyWordRepository;
import travelfeeldog.domain.review.reviewkeyword.dao.ReviewGoodKeyWordRepository;
import travelfeeldog.domain.review.reviewkeyword.dto.ReviewKeyWordDtos.ReviewKeyWordResponseByCategoryDto;
import travelfeeldog.domain.review.reviewkeyword.dto.ReviewKeyWordDtos.ReviewKeyWordResponseDto;

import travelfeeldog.domain.review.keyword.service.KeyWordService;

import travelfeeldog.domain.review.reviewkeyword.model.ReviewBadKeyWord;
import travelfeeldog.domain.review.reviewkeyword.model.ReviewGoodKeyWord;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ReviewKeyWordService {
    private final ReviewBadKeyWordRepository reviewBadKeyWordRepository;
    private final ReviewGoodKeyWordRepository reviewGoodKeyWordRepository;
    private final KeyWordService keyWordService;
    private final PlaceService placeService;
    private final MemberService memberService;

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
    public ReviewKeyWordResponseByCategoryDto getGoodOrBadKeyWordsByPlace(Long placeId, String givenKeyWord,String token) {
        memberService.findByToken(token);
        Place place = placeService.getPlaceById(placeId);
        List<Long> reviewIds = place.getReviews().stream().map(Review::getId).toList();
        KeyWordResponseByCategoryDto allKeyWords = keyWordService.getAllKeyWordsByCategory(place.getCategory().getId());

        if(givenKeyWord.equals("GOOD")) {
            List<ReviewKeyWordResponseDto> keyWords = setGood(allKeyWords,reviewIds);
            return new ReviewKeyWordResponseByCategoryDto(keyWords);
        }
        else {
            List<ReviewKeyWordResponseDto> keyWords = setBad(allKeyWords,reviewIds);
            return new ReviewKeyWordResponseByCategoryDto(keyWords);
        }
    }
    private List<ReviewKeyWordResponseDto> setGood(KeyWordResponseByCategoryDto allKeyWords,List<Long> reviewIds){
        List<ReviewKeyWordResponseDto> keyWords = allKeyWords.getGoodKeyWords().stream().map(ReviewKeyWordResponseDto::new).collect(Collectors.toList());
        for(Long reviewId :reviewIds){
            update(reviewGoodKeyWordRepository.getAllGoodKeyWordIds(reviewId),keyWords);
        }
        return keyWords;
    }
    private List<ReviewKeyWordResponseDto> setBad(KeyWordResponseByCategoryDto allKeyWords,List<Long> reviewIds){
        List<ReviewKeyWordResponseDto> keyWords = allKeyWords.getBadKeyWords().stream().map(ReviewKeyWordResponseDto::new).collect(Collectors.toList());
        for(Long reviewId :reviewIds){
            update(reviewBadKeyWordRepository.getAllBadKeyWordIds(reviewId),keyWords);
        }
        return keyWords;
    }
    private void update(List<Long> keyWordIds,List<ReviewKeyWordResponseDto> keyWords){
        for(Long item : keyWordIds){
            for(ReviewKeyWordResponseDto dto : keyWords){
                if(Objects.equals(dto.getKeyWordId(), item)){
                    int num = dto.getKeyWordCount();
                    num+=1;
                    dto.setKeyWordCount(num);
                }
            }

        }
    }
}






















