package org.filatov.api;


import lombok.RequiredArgsConstructor;
import org.filatov.model.DotaHero;
import org.filatov.model.DotaItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class HeroStats {

    Logger LOG = LoggerFactory.getLogger(HeroStats.class);

    private final WebClient webClient;

    public Flux<DotaHero> getHeroes(){
        return webClient
                .get()
                .uri(String.join("", "heroes"))
                .retrieve()
                .bodyToFlux(DotaHero.class);
    }

    public Flux<String> getHeroMatchups(String id) {
        return webClient
                .get()
                .uri(String.join("/", "heroes", id, "matchups"))
                .retrieve()
                .bodyToFlux(String.class);
    }

    public Mono<DotaItems > getPopularItems(String heroId) {
        return webClient
                .get()
                .uri(String.join("/", "heroes", heroId, "itemPopularity"))
                .retrieve()
                .bodyToMono(DotaItems.class);
    }
}
