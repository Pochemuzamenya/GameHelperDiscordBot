package org.filatov.listener;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import lombok.RequiredArgsConstructor;
import org.filatov.service.command.SlashCommandExecutor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class SlashCommandListener implements EventListener<ChatInputInteractionEvent>{

    private final SlashCommandExecutor executor;
    @Override
    public Class<ChatInputInteractionEvent> getEventType() {
        return ChatInputInteractionEvent.class;
    }

    @Override
    public Mono<Void> execute(ChatInputInteractionEvent event) {
        return executor.execute(event);
    }
}
