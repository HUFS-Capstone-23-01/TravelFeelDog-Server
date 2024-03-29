package travelfeeldog.review.presentation;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import travelfeeldog.review.dto.KeyWordDtos.KeyWordResponseByCategoryDto;
import travelfeeldog.review.dto.KeyWordDtos.KeyWordResponseDto;
import travelfeeldog.review.keyword.service.KeyWordService;
import travelfeeldog.global.common.dto.ApiResponse;

@RestController
@RequestMapping("/keyword")
@RequiredArgsConstructor
public class KeyWordApiController {
    private final KeyWordService keyWordService;

    @PostMapping(value = "/{type}", produces = "application/json;charset=UTF-8")
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

    @GetMapping(value = "/good", produces = "application/json;charset=UTF-8")
    public ResponseEntity<List<KeyWordResponseDto>> getAllGoodKeyWords() {
        List<KeyWordResponseDto> goodKeyWords = keyWordService.getAllGoodKeyWords();
        return ResponseEntity.ok(goodKeyWords);
    }

    @GetMapping(value = "/bad")
    public ResponseEntity<List<KeyWordResponseDto>> getAllBadKeyWords() {
        List<KeyWordResponseDto> badKeyWords = keyWordService.getAllBadKeyWords();
        return ResponseEntity.ok(badKeyWords);
    }

    @GetMapping(value = "/{categoryId}", produces = "application/json;charset=UTF-8")
    ApiResponse<KeyWordResponseByCategoryDto> getAllKeyWordsByCategory(@PathVariable Long categoryId) {
        return ApiResponse.success(keyWordService.getAllKeyWordsByCategory(categoryId));
    }
}

