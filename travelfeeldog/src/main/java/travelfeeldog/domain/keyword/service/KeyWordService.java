package travelfeeldog.domain.keyword.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travelfeeldog.domain.category.dao.CategoryRepository;
import travelfeeldog.domain.category.model.Category;
import travelfeeldog.domain.keyword.dto.KeyWordDtos.KeyWordResponseDto;
import travelfeeldog.domain.keyword.model.BadKeyWord;
import travelfeeldog.domain.keyword.model.GoodKeyWord;
import travelfeeldog.domain.keyword.repository.BadKeyWordRepository;
import travelfeeldog.domain.keyword.repository.GoodKeyWordRepository;

@Service
@RequiredArgsConstructor
public class KeyWordService {
    private final BadKeyWordRepository badKeyWordRepository;
    private final GoodKeyWordRepository goodKeyWordRepository;
    private final CategoryRepository categoryRepository;

    public KeyWordResponseDto saveBadKeyWord(String keyWord,String categoryName) {
        BadKeyWord badKeyWord = new BadKeyWord();
        Category category = categoryRepository.findByName(categoryName);
        badKeyWord.setCategory(category);
        badKeyWord.setKeyWord(keyWord);
        badKeyWordRepository.save(badKeyWord);
        return new KeyWordResponseDto(badKeyWord);
    }
    public KeyWordResponseDto saveGoodKeyWord(String keyWord,String categoryName) {
        GoodKeyWord goodKeyWord = new GoodKeyWord();
        Category category = categoryRepository.findByName(categoryName);
        goodKeyWord.setCategory(category);
        goodKeyWord.setKeyWord(keyWord);
        goodKeyWordRepository.save(goodKeyWord);

        return new KeyWordResponseDto(goodKeyWord);
    }
    public List<KeyWordResponseDto> getAllBadKeyWords(){
        return badKeyWordRepository.findAll().stream().map(KeyWordResponseDto::new).collect(Collectors.toList());
    }
    public List<KeyWordResponseDto> getAllGoodKeyWords(){
        return goodKeyWordRepository.findAll().stream().map(KeyWordResponseDto::new).collect(Collectors.toList());
    }
}
