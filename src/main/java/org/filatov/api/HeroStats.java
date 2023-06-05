package org.filatov.api;


import lombok.RequiredArgsConstructor;
import org.filatov.model.Hero;
import org.filatov.model.PopularItems;
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

    public Flux<Hero> getHeroes(){
        return webClient
                .get()
                .uri(String.join("", "heroes"))
                .retrieve()
                .bodyToFlux(Hero.class);
    }

    public Flux<String> getHeroMatchups(String id) {
        return webClient
                .get()
                .uri(String.join("/", "heroes", id, "matchups"))
                .retrieve()
                .bodyToFlux(String.class);
    }

    public Mono<PopularItems> getPopularItems(String heroId) {
        return webClient
                .get()
                .uri(String.join("/", "heroes", heroId, "itemPopularity"))
                .retrieve()
                .bodyToMono(PopularItems.class);
    }
}
