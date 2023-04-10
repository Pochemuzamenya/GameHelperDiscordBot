package org.filatov.handlers;

import discord4j.core.object.entity.Message;
import lombok.RequiredArgsConstructor;
import org.filatov.api.HeroStats;
import org.filatov.model.DotaItems;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DotaStatCommandHandler implements CommandHandler{

    private final HeroStats heroStatsService;

    private final UserInputHandler inputHandler;

    @Override
    public Mono<String> handleCommand(Message event)  {

        Mono<DotaItems> dotaItemsMono = getHeroName(event)
                .flatMap(
                        name -> heroStatsService
                                .getHeroes().filter(
                                        dotaHero ->
                                                dotaHero.getLocalized_name()
                                                        .equalsIgnoreCase(name)
                                )
                                .elementAt(0)
                )
                .flatMap(
                        hero -> heroStatsService
                                .getPopularItems(String.valueOf(hero.getId()))
                );
        return dotaItemsMono.map(DotaItems::toString);
    }

    private Mono<String> getHeroName(Message event) {
        return inputHandler
                .getParams(event.getContent())
                .elementAt(0)
                .onErrorResume(err-> {
                    LOG.error("Error: {}", err.getMessage());
                    return Mono.empty();
                });
    }

    @Override
    public String getMyCommandName() {
        return "stat";
    }

}
