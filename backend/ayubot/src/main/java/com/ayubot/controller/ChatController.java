package com.ayubot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ChatController {

    @Value("${groq.api.key}")
    private String GROQ_API_KEY;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody Map<String, String> request) {
        String userMessage = request.get("message");

        // Medical-safe system prompt (as per your synopsis: evidence-based, disclaimer)
        String fullPrompt = """
            You are AyuBot, a preliminary AI medical assistant .
            Provide instant insights only. Use evidence-based suggestions. Always end with: "This is not a substitute for professional medical advice. Consult a doctor immediately."
            
            User symptom: %s
            
            Respond empathetically, clearly, in simple language. Use bullet points for suggestions.
            """.formatted(userMessage);

        // Fully escape the prompt for JSON safety
        String escapedPrompt = fullPrompt.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");

        // Compact JSON to avoid multi-line issues
        String jsonBody = "{\"messages\":[{\"role\":\"system\",\"content\":\"You are a safe, accurate medical AI. Base responses on general knowledge. Prioritize user safety.\"},{\"role\":\"user\",\"content\":\"" + escapedPrompt + "\"}],\"model\":\"llama-3.1-8b-instant\",\"temperature\":0.5,\"max_tokens\":500}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(GROQ_API_KEY);

        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                "https://api.groq.com/openai/v1/chat/completions",
                HttpMethod.POST,
                entity,
                String.class
            );

            // Parse response safely
            JsonNode root = mapper.readTree(response.getBody());
            String aiReply = root.path("choices").get(0).path("message").path("content").asText();

            return ResponseEntity.ok(aiReply);

        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body("AI service temporarily unavailable. Check your Groq key or try again.\nError: " + e.getMessage());
        }
    }
}