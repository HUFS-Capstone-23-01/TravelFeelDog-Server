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
        List<Map<String, String>> placesNameAndDes = places.stream()
                .map(p -> Map.of(p.getName(), p.getDescribe())) // Map으로 변환하여 이름과 설명을 저장합니다
                .collect(Collectors.toList());
        Collections.shuffle(placesNameAndDes);
        List<Map<String, String>> maxFiveElements = placesNameAndDes.subList(0, Math.min(3, placesNameAndDes.size()));
        String selectedPlaces = maxFiveElements.stream()
                .map(map -> String.join(": ", map.keySet().iterator().next(), map.values().iterator().next())) // 이름과 설명을 ":"로 구분하여 연결합니다
                .collect(Collectors.joining(", ")); // 각 결과를 ", "로 구분하여 연결합니다
        String query = String.format("%s 들 기반으로 질문에 답해줘 Q :%s", selectedPlaces, prompt);
        System.out.println(query);

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
        String responseString = response.toString();

        System.out.println(responseString);

        String parsedResult = "";
        int start = responseString.indexOf("choices=[{text=");
        int end = responseString.indexOf("index=", start);

        if (start != -1 && end != -1) {
            parsedResult = responseString.substring(start, end).trim();
        }
        int cut = parsedResult.indexOf("A :");

        if (cut != -1 ) {
            parsedResult = parsedResult.substring(cut + 3).trim();
        }
        String result = parsedResult.replaceFirst("text=\\s*\\n\\s*\\d+\\.", "").replace("\n", "");
        if(result.isEmpty()){
            return selectedPlaces;
        }
        else {
            return result;
        }
    }
}
