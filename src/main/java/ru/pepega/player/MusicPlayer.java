package ru.pepega.player;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.playback.NonAllocatingAudioFrameBuffer;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Member;
import discord4j.voice.AudioProvider;
import reactor.core.publisher.Mono;
import ru.pepega.LavaPlayerAudioProvider;
import ru.pepega.AudioLoadHandler;
import ru.pepega.TrackScheduler;

import java.util.Arrays;

public class MusicPlayer implements Player{

    public int volume;

    String identifier = "https://www.youtube.com/watch?v=onBcln5tMQw&ab_channel=JAZZ%26BLUESJAZZ%26BLUES";

    final AudioPlayerManager playerManager = new DefaultAudioPlayerManager();

    final AudioPlayer player = playerManager.createPlayer();

    final TrackScheduler trackScheduler = new TrackScheduler(player);

    final AudioLoadHandler handler = new AudioLoadHandler(player);

    final AudioProvider provider = new LavaPlayerAudioProvider(player);

    {
         player.addListener(trackScheduler);
        playerManager.getConfiguration().setFrameBufferFactory(NonAllocatingAudioFrameBuffer::new);

        AudioSourceManagers.registerRemoteSources(playerManager);
    }

    @Override
    public Mono<Void> play(MessageCreateEvent event) {
        return Mono.justOrEmpty(event.getMessage().getContent())
                .map(content -> Arrays.asList(content.split(" ")))
                .doOnNext(command -> playerManager.loadItem(identifier, handler))
                        .then();
    }

    @Override
    public Mono<Void> stop(MessageCreateEvent event) {
        return Mono.justOrEmpty(event.getMessage().getContent())
                .map(c->Arrays.asList(c.split(" ")))
                .doOnNext(command -> player.stopTrack())
                .then();
    }

    @Override
    public Mono<Void> setVolume(MessageCreateEvent event, int volume) {
        return Mono.justOrEmpty(event.getMessage().getContent())
                .map(c->Arrays.asList(c.split(" ")))
                .doOnNext(command -> player.setVolume(volume))
                .then();
    }

    public Mono<Void> join(MessageCreateEvent event){
        return Mono.justOrEmpty(event.getMember())
                .flatMap(Member::getVoiceState)
                .flatMap(VoiceState::getChannel)
                .flatMap(channel -> channel.join(spec -> spec.setProvider(provider)))
                .then();
    }

    /*public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }*/
}
