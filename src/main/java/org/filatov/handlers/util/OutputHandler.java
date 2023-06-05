package org.filatov.handlers.util;

import lombok.RequiredArgsConstructor;
import org.filatov.model.PopularItems;
import org.filatov.model.PopularItemsDTO;
import org.filatov.service.util.FileConstantUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OutputHandler {

    private final Logger LOG = LoggerFactory.getLogger(OutputHandler.class);

    private final FileConstantUtils fileUtils;

    private final String CDN = "https://cdn.cloudflare.steamstatic.com/";

    public Flux<String> output(PopularItems items) {

        Set<String> idStartGameSet = items.getStart_game_items().keySet();
        Set<String> idEarlyGameSet = items.getEarly_game_items().keySet();
        Set<String> idMidGameSet = items.getMid_game_items().keySet();
        Set<String> idLateGameSet = items.getLate_game_items().keySet();

        Mono<List<String>> startGame = getListItems(idStartGameSet).collectList();
        Mono<List<String>> earlyGame = getListItems(idEarlyGameSet).collectList();
        Mono<List<String>> midGame = getListItems(idMidGameSet).collectList();
        Mono<List<String>> lateGame = getListItems(idLateGameSet).collectList();

        return Flux.zip(startGame, earlyGame, midGame, lateGame)
                .flatMap(tuple ->
                        Flux.just(PopularItemsDTO.builder()
                                .startGame(tuple.getT1())
                                .earlyGame(tuple.getT2())
                                .midGame(tuple.getT3())
                                .lateGame(tuple.getT4())
                                .build()
                        )).map(PopularItemsDTO::toString);
    }

    private Flux<String> getListItems(Set<String> idGameSet) {
        Map<String, String> idItems = fileUtils.getItemIdsConstant("ids.json");

        return Flux
                .fromIterable(idGameSet)
                .map(idItems::get);
    }

}
