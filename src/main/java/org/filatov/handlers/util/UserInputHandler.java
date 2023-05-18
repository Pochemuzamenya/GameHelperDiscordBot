package org.filatov.handlers.util;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UserInputHandler {

    private final Logger LOG = LoggerFactory.getLogger(UserInputHandler.class);

    public Mono<String> getCommandName(String userInput) {

        Mono<String> command = Flux
                .fromArray(
                        userInput.split(" ")
                ).elementAt(0)
                .map(el-> el.substring(1));

        command.subscribe(com -> LOG.debug("Command: {}", com));

        return command;
    }

    public Flux<String> getParams(String userInput) {
        Flux<String> params = Flux
                .fromArray(userInput.split(" "))
                .filter(el -> !el.startsWith("!"))
                .onErrorReturn("Команда без параметров");

        params.subscribe(p -> LOG.debug("Params: {}", p));
        return params;
    }


}
