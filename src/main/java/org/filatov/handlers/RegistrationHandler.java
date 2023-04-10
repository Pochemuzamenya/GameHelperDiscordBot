package org.filatov.handlers;

import discord4j.core.object.entity.Message;
import lombok.RequiredArgsConstructor;
import org.filatov.model.Member;
import org.filatov.service.command.RegistrationService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RegistrationHandler implements CommandHandler {

    private final RegistrationService service;

    @Override
    public Mono<String> handleCommand(Message event) {
        return service
                .register(event)
                .map(Member::getDiscordUsername);
    }

    @Override
    public String getMyCommandName() {
        return "reg";
    }
}
