package travelfeeldog.domain.place.place.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import travelfeeldog.domain.place.place.model.Place;
@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceGptSearchService {
    private final PlaceService placeService;
    @Value("${gpt.key}")
    private String API_KEY;
    private static final String END_POINT = "https://api.openai.com/v1/completions";

    public String answerText(String prompt, float temperature, int maxTokens) {
        List<Place> places = placeService.getAllPlaces();
        List<String> selectedPlaces = places.stream()
                .map(p -> p.getName() + ":" + p.getDescribe())
                .collect(Collectors.toList());

        Collections.shuffle(selectedPlaces);
        selectedPlaces = selectedPlaces.subList(0, Math.min(3, selectedPlaces.size()));

        System.out.println(selectedPlaces);
        log.info("selectedplace: {}", selectedPlaces);

        selectedPlaces = selectedPlacesDescribeHandles(selectedPlaces);
        String query = String.format("%s 들 기반으로 질문에 답해줘 Q: %s", String.join(", ", selectedPlaces), prompt);
        log.info("Query: {}", query);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "text-davinci-003");
        requestBody.put("prompt", query);
        requestBody.put("temperature", temperature);
        requestBody.put("max_tokens", maxTokens);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map> response = restTemplate.postForEntity(END_POINT, requestEntity, Map.class);
        Map<String, Object> responseBody = response.getBody();

        List<Map<String, String>> choices = (List<Map<String, String>>) responseBody.get("choices");
        if (choices != null && !choices.isEmpty()) {
            String text = choices.get(0).get("text");
            String result = extractResultFromText(text);
            if (result != null) {
                log.info("Result: {}", result);
                return result;
            }
        }

        return String.join(", ", selectedPlaces);
    }

    public List<String> selectedPlacesDescribeHandles(List<String> selectedPlaces) {
        List<String> parsedScriptPlaces = new ArrayList<>();
        for(String place : selectedPlaces) {
            int end = place.indexOf(".");
            if(end != -1)
            {
                parsedScriptPlaces.add(place.substring(0,end+1));
            }
            else {
                parsedScriptPlaces.add(place);
            }
        }
        return parsedScriptPlaces;
    }

    private String extractResultFromText(String text) {
        int lastIndex = text.lastIndexOf("A:");
        if (lastIndex != -1) {
            return text.substring(lastIndex + 2).replaceAll("A :", "").replaceAll("\\n", "").trim();
        }

        int lastIndex1 = text.lastIndexOf("A :");
        if (lastIndex1 != -1) {
            return text.substring(lastIndex1 + 3).replaceAll("A :", "").replaceAll("\\n", "").trim();
        }

        return null;
    }

}
