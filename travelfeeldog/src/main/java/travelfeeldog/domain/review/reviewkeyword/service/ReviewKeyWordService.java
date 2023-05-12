package travelfeeldog.domain.review.reviewkeyword.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelfeeldog.domain.review.keyword.service.KeyWordService;
import travelfeeldog.domain.review.review.model.Review;
import travelfeeldog.domain.review.reviewkeyword.dao.ReviewGoodKeyWordRepository;
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

    @Transactional
    public void saveReviewKeyWords(List<Long> badKeyWordIds , List<Long> goodKeyWordIds, Review review){
        for(Long id : badKeyWordIds){
            reviewBadKeyWordRepository.save(new ReviewBadKeyWord(review,keyWordService.getBadKeyWordById(id)));
        }
        for(Long id : goodKeyWordIds){
            reviewGoodKeyWordRepository.save((new ReviewGoodKeyWord(review,keyWordService.getGoodKeyWordById(id))));
        }
    }

}
