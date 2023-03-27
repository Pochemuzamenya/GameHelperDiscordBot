package org.filatov.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.logging.Level;


@Service
public class WebClientService implements OpenDotaService {

    private WebClient webClient;

    @Value("${steam_id}")
    private String steamId;

    @PostConstruct
    private void setUpWebClient() {
        webClient = WebClient.create("https://api.opendota.com/api/");
        log.info("WebClient created");
    }

    public Mono<String> winLose() {
        log.info("win/lose for " + steamId);

        return webClient
                .get()
                .uri("/players/{account_id}/wl", steamId)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> wardMap() {
        log.info("ward map for " + steamId);

        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/players/{account_id}/wardmap")
                        .queryParam("limit", "{limit}")
                        .build(steamId, "5"))
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> wordCloud(){
        log.info("word cloud for " + steamId);

        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/players/{account_id}/wordcloud")
                        .queryParam("limit", "{limit}")
                        .build(steamId, "5"))
                .retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public Mono<String> refresh() {
        return webClient
                .post()
                .uri("/players/{account_id}/refresh", steamId)
                .retrieve()
                .bodyToMono(String.class)
                .log("Call refresh", Level.FINE);
    }
}
