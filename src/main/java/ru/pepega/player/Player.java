package ru.pepega.player;

import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;

public interface Player {
    Mono<Void> play(MessageCreateEvent event);
    Mono<Void> stop(MessageCreateEvent event);
    Mono<Void> setVolume(MessageCreateEvent event, int volume);
}
