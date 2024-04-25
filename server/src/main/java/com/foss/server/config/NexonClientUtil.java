package com.foss.server.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class NexonClientUtil {
    private final WebClient.Builder webClientBuilder;

    @Value("${api-key}")
    private String API_KEY;
    private final static String API_HEADER = "x-nxopen-api-key";
    @Value("${nexon-api-url")
    private String API_BASE_URL;

    public WebClient createNexonWebClient() {
        return webClientBuilder.baseUrl(API_BASE_URL)
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader(API_HEADER, API_KEY)
                .build();
    }

}
