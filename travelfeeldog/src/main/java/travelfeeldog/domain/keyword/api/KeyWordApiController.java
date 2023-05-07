package travelfeeldog.domain.keyword.api;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import travelfeeldog.domain.keyword.dto.KeyWordDtos.KeyWordResponseDto;
import travelfeeldog.domain.keyword.model.BadKeyWord;
import travelfeeldog.domain.keyword.model.GoodKeyWord;
import travelfeeldog.domain.keyword.service.KeyWordService;
@RestController
@RequestMapping("/keywords")
@RequiredArgsConstructor
public class KeyWordApiController {
    private final KeyWordService keyWordService;

    @PostMapping(value = "/{type}",produces = "application/json;charset=UTF-8")
    public ResponseEntity<KeyWordResponseDto> addKeyWord(@PathVariable String type,
                                   @RequestParam String keyWord,
                                   @RequestParam String categoryName) {
        if (type.equals("good")) {
            KeyWordResponseDto goodKeyWord = keyWordService.saveGoodKeyWord(keyWord, categoryName);
            return ResponseEntity.ok(goodKeyWord);
        } else if (type.equals("bad")) {
            KeyWordResponseDto badKeyWord = keyWordService.saveBadKeyWord(keyWord, categoryName);
            return ResponseEntity.ok(badKeyWord);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/good",produces = "application/json;charset=UTF-8")
    public ResponseEntity<List<KeyWordResponseDto>> getAllGoodKeyWords() {
        List<KeyWordResponseDto> goodKeyWords = keyWordService.getAllGoodKeyWords();
        return ResponseEntity.ok(goodKeyWords);
    }

    @GetMapping(value = "/bad")
    public ResponseEntity<List<KeyWordResponseDto>> getAllBadKeyWords() {
        List<KeyWordResponseDto> badKeyWords = keyWordService.getAllBadKeyWords();
        return ResponseEntity.ok(badKeyWords);
    }
}

