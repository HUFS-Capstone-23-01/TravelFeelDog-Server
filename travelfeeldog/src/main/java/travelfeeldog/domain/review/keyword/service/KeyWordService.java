package travelfeeldog.domain.review.keyword.service;

import java.util.List;
import java.util.stream.Collectors;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travelfeeldog.domain.place.category.model.Category;
import travelfeeldog.domain.place.category.service.CategoryService;
import travelfeeldog.domain.review.keyword.dto.KeyWordDtos.KeyWordResponseByCategoryDto;
import travelfeeldog.domain.review.keyword.dto.KeyWordDtos.KeyWordResponseDto;
import travelfeeldog.domain.review.keyword.model.BadKeyWord;
import travelfeeldog.domain.review.keyword.model.GoodKeyWord;
import travelfeeldog.domain.review.keyword.repository.BadKeyWordRepository;
import travelfeeldog.domain.review.keyword.repository.GoodKeyWordRepository;

@Service
@RequiredArgsConstructor
public class KeyWordService {
    private final BadKeyWordRepository badKeyWordRepository;
    private final GoodKeyWordRepository goodKeyWordRepository;
    private final CategoryService categoryService;
    public GoodKeyWord getGoodKeyWordById(Long id){
        return goodKeyWordRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("not found with ID: " + id));
    }
    public BadKeyWord getBadKeyWordById(Long id){
        return badKeyWordRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("not found with ID: " + id));
    }
    public KeyWordResponseDto saveBadKeyWord(String keyWord,String categoryName) {
        BadKeyWord badKeyWord = new BadKeyWord();
        Category category = categoryService.getCategoryByName(categoryName);
        badKeyWord.setCategory(category);
        badKeyWord.setKeyWordName(keyWord);
        badKeyWordRepository.save(badKeyWord);
        return new KeyWordResponseDto(badKeyWord);
    }
    public KeyWordResponseDto saveGoodKeyWord(String keyWord,String categoryName) {
        GoodKeyWord goodKeyWord = new GoodKeyWord();
        Category category = categoryService.getCategoryByName(categoryName);
        goodKeyWord.setCategory(category);
        goodKeyWord.setKeyWordName(keyWord);
        goodKeyWordRepository.save(goodKeyWord);

        return new KeyWordResponseDto(goodKeyWord);
    }
    public List<KeyWordResponseDto> getAllBadKeyWords(){
        return badKeyWordRepository.findAll().stream().map(KeyWordResponseDto::new).collect(Collectors.toList());
    }
    public List<KeyWordResponseDto> getAllGoodKeyWords(){
        return goodKeyWordRepository.findAll().stream().map(KeyWordResponseDto::new).collect(Collectors.toList());
    }
    public KeyWordResponseByCategoryDto getAllKeyWordsByCategory(Long categoryId){
        List<GoodKeyWord> goodKeyWords = goodKeyWordRepository.findAllByCategoryId(categoryId);
        List<BadKeyWord> badKeyWords = badKeyWordRepository.findAllByCategoryId(categoryId);
        return new KeyWordResponseByCategoryDto(goodKeyWords,badKeyWords);
    }
}
