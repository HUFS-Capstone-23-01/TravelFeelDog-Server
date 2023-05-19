package travelfeeldog.domain.place.place.service;

import com.theokanning.openai.completion.chat.ChatMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import travelfeeldog.domain.place.place.dto.PlaceDtos.GptChatMessage;
import travelfeeldog.domain.place.place.model.Place;

@Service
@RequiredArgsConstructor
public class PlaceGptSearchService {
    private final PlaceService placeService;
    @Value("${gpt.key}")
    private String API_KEY;
    private static final String END_POINT = "https://api.openai.com/v1/chat/completions";

    public String answerText(String prompt, float temperature, int maxTokens) {
        List<Place> places = placeService.getAllPlaces();
        List<String> placesName = places.stream().map(i->i.getName()).collect(Collectors.toList());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);

        String query = placesName + "Base on this  information" + prompt;
        List<GptChatMessage> messages = new ArrayList<>();

        messages.add(new GptChatMessage("user", query));

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("messages", messages);
        requestBody.put("model","gpt-3.5-turbo");
        requestBody.put("temperature", temperature);
        requestBody.put("max_tokens",maxTokens);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(END_POINT, requestEntity, Map.class);
        Map<String, Object> responseBody = response.getBody();

        return responseBody.toString();
    }
}
