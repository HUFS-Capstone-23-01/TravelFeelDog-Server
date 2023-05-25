package travelfeeldog.domain.place.place.service;

import java.util.ArrayList;
import java.util.Collections;
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

import travelfeeldog.domain.place.place.model.Place;

@Service
@RequiredArgsConstructor
public class PlaceGptSearchService {
    private final PlaceService placeService;
    @Value("${gpt.key}")
    private String API_KEY;
    private static final String END_POINT = "https://api.openai.com/v1/completions";

    public String answerText(String prompt, float temperature, int maxTokens) {

        List<Place> places = placeService.getAllPlaces();
        List<String> placesName = places.stream().map(Place::getName).collect(Collectors.toList());
        Collections.shuffle(placesName);
        List<String> maxFiveElements = placesName.subList(0, Math.min(1, placesName.size()));
        String selectedPlaces = String.join(",",maxFiveElements);
        String query = String.format("%s Based on this information, %s", selectedPlaces, prompt);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model","text-davinci-003");
        requestBody.put("prompt", query);
        requestBody.put("temperature", temperature);
        requestBody.put("max_tokens", maxTokens);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(END_POINT, requestEntity, Map.class);
        return response.toString();
    }
}
