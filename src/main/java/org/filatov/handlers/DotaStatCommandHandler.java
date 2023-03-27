package org.filatov.handlers;

import discord4j.core.object.entity.Message;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DotaStatCommandHandler implements CommandHandler{

    @Override
    public Mono<String> handleCommand(Message message) {
        return Mono.just("Hello");
    }

    @Override
    public String getMyCommandName() {
        return "!stat";
    }

}
