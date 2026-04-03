package com.example.Medical.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Map;

@Service
public class AIService {

    private final WebClient webClient = WebClient.create("https://api.openai.com");

    @Value("${openai.api.key:}")
    private String apiKey;

    @Value("${openai.model:gpt-4o-mini}")
    private String model;

    public String askAI(String query) {

        return askAI(List.of(Map.of("role", "user", "content", query)), query);
    }

    public String askAI(List<Map<String, String>> messages, String latestQuery) {

        if (latestQuery == null || latestQuery.isBlank()) {
            return "Please enter a question.";
        }

        if (apiKey == null || apiKey.isBlank()) {
            return fallbackAnswer(latestQuery);
        }

        try {
            List<Map<String, String>> payloadMessages = new java.util.ArrayList<>();
            payloadMessages.add(Map.of("role", "system", "content", "You are a medical assistant helping doctors. Use the conversation history to keep context."));
            payloadMessages.addAll(messages);

            Map<String, Object> response = webClient.post()
                    .uri("/v1/chat/completions")
                    .header("Authorization", "Bearer " + apiKey)
                    .bodyValue(Map.of(
                            "model", model,
                            "messages", payloadMessages
                    ))
                    .retrieve()
                .bodyToMono(Map.class)
                    .block();

            return extractAssistantMessage(response);

        } catch (WebClientResponseException ex) {
            if (ex.getStatusCode().value() == 401) {
                return "OpenAI authentication failed. Please verify OPENAI_API_KEY.";
            }
            if (ex.getStatusCode().value() == 429) {
                return "OpenAI rate limit or quota exceeded. Please check billing/quota.";
            }
            return "OpenAI request failed with status " + ex.getStatusCode().value() + ".";

        } catch (Exception e) {
            return fallbackAnswer(latestQuery);
        }
    }

    private String extractAssistantMessage(Map<String, Object> responseBody) {
        try {
            if (responseBody == null) {
                return "I could not generate an answer right now.";
            }

            Object choicesObj = responseBody.get("choices");
            if (!(choicesObj instanceof List<?> choices) || choices.isEmpty()) {
                return "I could not generate an answer right now.";
            }

            Object firstChoice = choices.get(0);
            if (!(firstChoice instanceof Map<?, ?> choiceMap)) {
                return "I could not generate an answer right now.";
            }

            Object messageObj = choiceMap.get("message");
            if (!(messageObj instanceof Map<?, ?> messageMap)) {
                return "I could not generate an answer right now.";
            }

            Object contentObj = messageMap.get("content");
            if (contentObj instanceof String content && !content.isBlank()) {
                return content;
            }

            return "I could not generate an answer right now.";
        } catch (Exception ignored) {
            return "I could not read the AI response format.";
        }
    }

    private String fallbackAnswer(String query) {
        String normalized = query.toLowerCase();

        if (normalized.contains("who is a doctor") || normalized.equals("doctor")) {
            return "A doctor is a licensed medical professional who diagnoses illnesses, advises patients, and provides treatment to improve health outcomes.";
        }

        if (normalized.contains("who is a pharmacist")) {
            return "A pharmacist is a licensed medicine expert who dispenses prescriptions, checks drug safety, and counsels patients on proper medication use.";
        }

        return "AI service is currently unavailable. Basic fallback is active. Set openai.api.key to enable full AI responses.";
    }
}