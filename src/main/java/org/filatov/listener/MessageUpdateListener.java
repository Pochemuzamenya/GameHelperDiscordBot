package org.filatov.listener;

import discord4j.core.event.domain.message.MessageUpdateEvent;
import org.filatov.service.command.CommandExecutor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MessageUpdateListener extends MessageListener implements EventListener<MessageUpdateEvent> {

    public MessageUpdateListener(CommandExecutor executor) {
        super(executor);
    }

    @Override
    public Class<MessageUpdateEvent> getEventType() {
        return MessageUpdateEvent.class;
    }

    @Override
    public Mono<Void> execute(MessageUpdateEvent event) {
        return Mono.just(event)
                .filter(MessageUpdateEvent::isContentChanged)
                .flatMap(MessageUpdateEvent::getMessage)
                .flatMap(super::processCommands);
    }
}
