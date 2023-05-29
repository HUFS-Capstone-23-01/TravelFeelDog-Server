package travelfeeldog.domain.place.place.dto;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class OpenAiResponse {
    private List<Map<String, String>> choices;
}