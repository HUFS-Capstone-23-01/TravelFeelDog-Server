package travelfeeldog.placeinformation.dto;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OpenAiResponse {
    private List<Map<String, String>> choices;
}
