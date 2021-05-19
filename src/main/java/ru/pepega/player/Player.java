package ru.pepega.player;

import discord4j.core.event.domain.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import ru.pepega.listener.EventListener;

public interface Player<T> {
    Logger LOG = LoggerFactory.getLogger(EventListener.class);

    Mono<Void> play(MessageCreateEvent event);

    Mono<Void> stop(MessageCreateEvent event);

    Mono<Void> setVolume(MessageCreateEvent event);

    Mono<Void> pause(MessageCreateEvent event);

    Mono<Void> resume(MessageCreateEvent event);

    Class<T> getEventType();

    default Mono<Void> playerError(Throwable error) {
        LOG.error("Unable to process " + getEventType().getSimpleName(), error);
        return Mono.empty();
    }
}
