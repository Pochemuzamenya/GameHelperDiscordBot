package ru.pepega.listener;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.pepega.player.MusicPlayer;
import ru.pepega.player.Player;

import java.util.Objects;

@Service
public class MessageCreateListener extends MessageListener implements EventListener<MessageCreateEvent> {

    MusicPlayer musicPlayer = new MusicPlayer();

    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        String message = Mono.just(event.getMessage())
                .filter(message1 -> message1.getAuthor().map(user -> !user.isBot()).orElse(false))
                .map(message1 -> message1.getContent())
                .block();

        if (message != null) {
            switch (message){
                case "!join":
                    return join(event);
                case "!play":
                    return musicPlayer.play(event);
                case "!stop":
                    return musicPlayer.stop(event);
                case "!pause":
                    return musicPlayer.pause(event);
                case "!resume":
                    return musicPlayer.resume(event);
                case "!volume":
                    return musicPlayer.setVolume(event);
                case "!todo":
                    return processCommand(event);

                default: return Mono.empty();
            }
        }
        return Mono.empty();
    }

    public Mono<Void> join(MessageCreateEvent event){
        return Mono.just(event.getMessage())
                .filter(message -> message.getContent().equalsIgnoreCase("!join"))
                .flatMap(content -> musicPlayer.join(event));
    }

    public Mono<Void> processCommand(MessageCreateEvent event) {
        return Mono.just(event.getMessage())
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("Я умею подрубать музыку!\n" +
                        "!join что бы присоедениться к голосовому каналу\n" +
                        "Список команд плеера:\n" +
                        "!play :arrow_forward:\n" +
                        "!stop :stop_button: \n" +
                        "!pause :play_pause: \n"))
                .then();
    }
}

