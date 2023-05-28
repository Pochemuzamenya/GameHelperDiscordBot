package org.filatov.handlers;

import discord4j.core.object.entity.Message;
import lombok.RequiredArgsConstructor;
import org.filatov.api.HeroStats;
import org.filatov.handlers.util.OutputHandler;
import org.filatov.handlers.util.UserInputHandler;
import org.filatov.model.DotaItems;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PopularItemCommandHandler implements CommandHandler<Message>{

    private final HeroStats heroStatsService;

    private final UserInputHandler inputHandler;

    private final OutputHandler outputHandler;

    @Override
    public Mono<Void> handleCommand(Message event)  {

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

        Mono<String> map = dotaItemsMono.
                flatMapMany(outputHandler::output)
                .collectList()
                .map(list -> String.join("", list));
        return Mono.empty();
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
        return "item";
    }

}
