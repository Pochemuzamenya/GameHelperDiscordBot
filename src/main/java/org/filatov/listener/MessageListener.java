package org.filatov.listener;

import discord4j.core.object.entity.Message;
import lombok.RequiredArgsConstructor;
import org.filatov.service.command.MessageCommandExecutor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public abstract class MessageListener {

    private final MessageCommandExecutor executor;

    public Mono<Void> processCommands(Message eventMessage) {

        return Mono.just(eventMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().startsWith("!"))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage(executor.execute(eventMessage).block()))
                .then();
    }
}
