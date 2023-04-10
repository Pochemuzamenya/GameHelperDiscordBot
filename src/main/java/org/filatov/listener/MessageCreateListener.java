package org.filatov.listener;

import discord4j.core.event.domain.message.MessageCreateEvent;
import org.filatov.service.command.MessageCommandExecutor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MessageCreateListener extends MessageListener implements EventListener<MessageCreateEvent>{

    public MessageCreateListener(MessageCommandExecutor executor) {
        super(executor);
    }

    @Override
    public Class getEventType() {
        return MessageCreateEvent.class;
    }

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        return processCommands(event.getMessage());
    }
}
