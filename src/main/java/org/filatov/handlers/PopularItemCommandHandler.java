package org.filatov.handlers;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import lombok.RequiredArgsConstructor;
import org.filatov.api.HeroStats;
import org.filatov.handlers.util.OutputHandler;
import org.filatov.handlers.util.UserInputHandler;
import org.filatov.model.PopularItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class PopularItemCommandHandler{
    Logger LOG = LoggerFactory.getLogger(this.getClass());

    private final HeroStats heroStatsService;

    private final UserInputHandler inputHandler;

    private final OutputHandler outputHandler;

    public Mono<Void> handleCommand(MessageCreateEvent event)  {

        Mono<PopularItems> dotaItemsMono = getHeroName(event.getMessage())
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

    public String getMyCommandName() {
        return "item";
    }

}
