package com.finance.finance.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class RatiosController {

    @Value("${groq.api.key}")
    private String groqApiKey;

    private final String GROQ_API_URL = "https://api.groq.com/openai/v1/chat/completions";

    @PostMapping("/analyse-ratios")
    public ResponseEntity<String> analyseRatios(@RequestBody String ratiosMessage) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            // Échapper les caractères spéciaux dans ratiosMessage
            String escapedRatiosMessage = escapeJsonString(ratiosMessage);

            // Créer le JSON à envoyer
            String jsonBody = """
                {
                    "messages": [
                        {"role": "system", "content": "Analyse ces données de ratios comptables."},
                        {"role": "user", "content": "%s"}
                    ],
                    "model": "llama-3.3-70b-versatile",
                    "temperature": 1,
                    "max_tokens": 1024,
                    "top_p": 1,
                    "stream": false
                }
            """.formatted(escapedRatiosMessage);

            // Créer les headers de la requête
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(groqApiKey);

            // Construire la requête
            HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

            // Envoyer la requête POST
            ResponseEntity<String> response = restTemplate.exchange(
                    GROQ_API_URL, HttpMethod.POST, request, String.class);

            // Vérifier la réponse de l'API
            if (response.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.ok(response.getBody());
            } else {
                return ResponseEntity.status(response.getStatusCode())
                        .body("Erreur de l'API Groq : " + response.getBody());
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Gérer les erreurs spécifiques liées à la requête HTTP
            return ResponseEntity.status(e.getStatusCode())
                    .body("Erreur HTTP lors de l'appel à l'API Groq: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            // Gérer les autres exceptions
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur interne lors de l'analyse des ratios");
        }
    }

    // Méthode pour échapper les caractères spéciaux dans une chaîne JSON
    private String escapeJsonString(String input) {
        if (input == null) {
            return "";
        }
        // Échapper les caractères spéciaux JSON (guillemets, antislashs, retours à la ligne, etc.)
        return input.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
    }
}
