package org.filatov.handlers;

import org.filatov.handlers.util.UserInputHandler;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class UserInputHandlerTest {

    Logger LOG = LoggerFactory.getLogger(UserInputHandlerTest.class);
    String testString = "!commandName param1 param2";
    String testStrinWoParams = "!CommandNoParams";
    UserInputHandler handler = new UserInputHandler();
    @Test
    void getCommandName() {

        Mono<String> commandName = handler.getCommandName(testString);

        StepVerifier.create(commandName)
                .expectNext("commandName")
                .verifyComplete();
    }

    @Test
    void getParams() {
        Flux<String> params = handler.getParams(testString);
        Flux<String> noParams = handler.getParams(testStrinWoParams);

        StepVerifier.create(params)
                .expectNext("param1")
                .expectNext("param2")
                .verifyComplete();

        StepVerifier.create(noParams)
                .expectComplete()
                .log();
    }
}