package com.dev.chatgpt.controller;

import com.dev.chatgpt.dto.ChatRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:3000") // React frontend URL
public class ChatController {

    @Value("${openai.api.key}")
    private String apiKey;

    @PostMapping
    public String chat(@RequestBody ChatRequest chatRequest) {
        String apiUrl = "https://api.openai.com/v1/chat/completions";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content", chatRequest.getMessage());

        JSONObject payload = new JSONObject();
        payload.put("model", "gpt-4o");
        payload.put("messages", new org.json.JSONArray().put(message));

        org.springframework.http.HttpEntity<String> request =
                new org.springframework.http.HttpEntity<>(payload.toString(), headers);

        try {
            org.springframework.http.ResponseEntity<String> response =
                    restTemplate.postForEntity(apiUrl, request, String.class);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\": \"Failed to fetch response from OpenAI API\"}";
        }
    }
}
