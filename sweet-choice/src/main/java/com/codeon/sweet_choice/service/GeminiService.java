package com.codeon.sweet_choice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.http.HttpHeaders;

@Service
public class GeminiService {
    private final WebClient webClient;
    private final String apiKey = System.getenv("GEMINI_API_KEY");

    public GeminiService(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("https://generativelanguage.googleapis.com/v1beta")
                .build();
    }

    public String analysisReport(String prompt){
        String body = """
        {
            "contents": [{
                "parts": [
                    { "text": "%s" }
                ]
            }]
        }
        """.formatted(prompt);

        return webClient.post()
                .url(urlBuilder -> urlBuilder
                        .path("/models/gemini-2.0-flash:generateContent")
                        .queryParam("key", apiKey)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
