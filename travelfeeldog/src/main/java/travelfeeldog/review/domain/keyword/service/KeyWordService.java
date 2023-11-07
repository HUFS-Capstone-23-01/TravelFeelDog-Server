package travelfeeldog.review.domain.keyword.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travelfeeldog.place.domain.information.category.model.Category;
import travelfeeldog.place.domain.information.category.service.CategoryService;
import travelfeeldog.review.domain.keyword.model.BadKeyWord;
import travelfeeldog.review.domain.keyword.model.BadKeyWords;
import travelfeeldog.review.domain.keyword.model.GoodKeyWord;
import travelfeeldog.review.domain.keyword.model.GoodKeyWords;
import travelfeeldog.review.domain.keyword.repository.BadKeyWordRepository;
import travelfeeldog.review.domain.keyword.repository.GoodKeyWordRepository;
import travelfeeldog.review.dto.KeyWordDtos.KeyWordResponseByCategoryDto;
import travelfeeldog.review.dto.KeyWordDtos.KeyWordResponseDto;

@Service
@RequiredArgsConstructor
public class KeyWordService {
    private final BadKeyWordRepository badKeyWordRepository;
    private final GoodKeyWordRepository goodKeyWordRepository;
    private final CategoryService categoryService;

    public GoodKeyWord getGoodKeyWordById(Long id) {
        return goodKeyWordRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("not found with ID: " + id));
    }

    public BadKeyWord getBadKeyWordById(Long id) {
        return badKeyWordRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("not found with ID: " + id));
    }

    public KeyWordResponseDto saveBadKeyWord(String keyWord, String categoryName) {
        Category category = categoryService.getCategoryByName(categoryName);
        BadKeyWord badKeyWord = new BadKeyWord(category, keyWord);
        badKeyWordRepository.save(badKeyWord);
        return new KeyWordResponseDto(badKeyWord);
    }

    public KeyWordResponseDto saveGoodKeyWord(String keyWord, String categoryName) {
        Category category = categoryService.getCategoryByName(categoryName);
        GoodKeyWord goodKeyWord = new GoodKeyWord(category, keyWord);
        goodKeyWordRepository.save(goodKeyWord);
        return new KeyWordResponseDto(goodKeyWord);
    }

    public List<KeyWordResponseDto> getAllBadKeyWords() {
        return badKeyWordRepository.findAll().stream().map(KeyWordResponseDto::new).toList();
    }

    public List<KeyWordResponseDto> getAllGoodKeyWords() {
        return goodKeyWordRepository.findAll().stream().map(KeyWordResponseDto::new).toList();
    }

    public KeyWordResponseByCategoryDto getAllKeyWordsByCategory(Long categoryId) {
        return new KeyWordResponseByCategoryDto(getGoodKeyWordByCategoryId(categoryId).getGoodKeyWords(),
                getBadKeyWordByCategoryId(categoryId).getBadKeyWords());
    }

    public GoodKeyWords getGoodKeyWordByCategoryId(Long categoryId) {
        List<GoodKeyWord> goodKeyWords = goodKeyWordRepository.findAllByCategoryId(categoryId);
        return new GoodKeyWords(goodKeyWords);
    }

    public BadKeyWords getBadKeyWordByCategoryId(Long categoryId) {
        List<BadKeyWord> badKeyWords = badKeyWordRepository.findAllByCategoryId(categoryId);
        return new BadKeyWords(badKeyWords);
    }
}
