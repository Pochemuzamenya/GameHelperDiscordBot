package ru.pepega.listener;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Member;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.pepega.player.MusicPlayer;
import ru.pepega.player.Player;

@Service
public class MessageCreateListener extends MessageListener implements EventListener<MessageCreateEvent> {

    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        MusicPlayer musicPlayer = new MusicPlayer();
        return Mono.just(event.getMessage())
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().equalsIgnoreCase("$join"))
                .flatMap(content -> musicPlayer.join(event));
        //return processCommand(event.getMessage());
    }
}
