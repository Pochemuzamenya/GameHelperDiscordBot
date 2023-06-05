package org.filatov.listener;

import discord4j.core.object.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public abstract class MessageListener {

    public Mono<Void> processCommands(Message eventMessage) {

        return Mono.just(eventMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().startsWith("!"))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("hi"))
                .then();
    }
}
