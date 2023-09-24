package travelfeeldog.place.domain.place.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import travelfeeldog.place.dto.OpenAiResponse;
import travelfeeldog.place.domain.place.model.Place;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceGptSearchService {
    private final PlaceService placeService;
    @Value("${gpt.key}")
    private String API_KEY;
    private static final String END_POINT = "https://api.openai.com/v1/completions";

    public String answerText(String prompt, float temperature, int maxTokens) {
       String selectedPlaces = buildSelectedPlaces();
        String query = buildGptQuery(selectedPlaces,prompt);
        HttpEntity<Map<String, Object>> requestEntity = buildRequestEntity(query, temperature, maxTokens);
        String result = postEntityToOpenApi(requestEntity);
        if(result.isEmpty())  {
            return "답변이 어렵지만 대신에 다음 장소들을 추천 합니다."+selectedPlaces;
        }
        return result;
    }
    public String postEntityToOpenApi(HttpEntity<Map<String, Object>> requestEntity) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<OpenAiResponse> responseEntity = restTemplate.exchange(
                    END_POINT,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<>() {}
            );
            OpenAiResponse openAiResponse = responseEntity.getBody();
            if (openAiResponse != null) {
                List<Map<String, String>> choices = openAiResponse.getChoices();
                log.info("gpt result choices:{}", choices);
                String text = choices.get(0).get("text");
                String result = extractResultFromText(text);
                if (!result.isEmpty()) {
                    log.info("Result: {}", result);
                    return result;
                }
            }
        } catch (HttpClientErrorException ignored) {
            log.error("Open APi Error");
        }
        return "";
    }
    public String buildGptQuery(String selectedPlaces,String prompt) {
        String query = String.format("%s = {장소: 설명.}를 기반으로 질문에 답해줘 Q: %s", selectedPlaces, prompt);
        log.info("Query: {}", query);
        return query;
    }
    public HttpEntity<Map<String, Object>>  buildRequestEntity(String query, float temperature, int maxTokens) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "text-davinci-003");
        requestBody.put("prompt", query);
        requestBody.put("temperature", temperature);
        requestBody.put("max_tokens", maxTokens);
        return new HttpEntity<>(requestBody, headers);
    }
    public String buildSelectedPlaces(){
        List<Place> places = placeService.getAllPlaces();
        List<String> selectedPlaces = places.stream()
                .map(p -> p.getName() + ":" + p.getDescribe())
                .collect(Collectors.toList());
        Collections.shuffle(selectedPlaces);
        selectedPlaces = selectedPlaces.subList(0, Math.min(3, selectedPlaces.size()));
        selectedPlaces = selectedPlacesDescribeHandles(selectedPlaces);
        selectedPlaces.stream().map(s->s.replaceAll("\n",""));
        log.info("selectedplace: {}", selectedPlaces);
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

    public String extractResultFromText(String text) {
        int lastIndex = text.lastIndexOf("A:");
        if (lastIndex != -1) {
            return text.substring(lastIndex + 2).replaceAll("A :", "").replaceAll("\\n", "").trim();
        }

        int lastIndex1 = text.lastIndexOf("A :");
        if (lastIndex1 != -1) {
            return text.substring(lastIndex1 + 3).replaceAll("A :", "").replaceAll("\\n", "").trim();
        }

        return "";
    }
}
