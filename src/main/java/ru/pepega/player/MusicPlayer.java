package ru.pepega.player;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
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

public class MusicPlayer implements Player {

    public int volume;

    String identifier = "https://open.spotify.com/playlist/37i9dQZF1E36DzRgMxnjZG";

    final AudioPlayerManager playerManager = new DefaultAudioPlayerManager();

    final AudioPlayer player = playerManager.createPlayer();

    final TrackScheduler trackScheduler = new TrackScheduler(player);

    final AudioLoadHandler handler = new AudioLoadHandler(player);

    AudioPlaylist audioPlaylist;

    final AudioProvider provider = new LavaPlayerAudioProvider(player);

    {
         player.addListener(trackScheduler);
        playerManager.getConfiguration().setFrameBufferFactory(NonAllocatingAudioFrameBuffer::new);

        AudioSourceManagers.registerRemoteSources(playerManager);
    }

    @Override
    public Mono<Void> play(MessageCreateEvent event) {
        return Mono.justOrEmpty(event.getMessage().getContent())
                .doOnNext(command -> playerManager.loadItem(identifier, handler))
                .onErrorResume(this::playerError)
                        .then();
    }

    @Override
    public Mono<Void> stop(MessageCreateEvent event) {
        return Mono.justOrEmpty(event.getMessage().getContent())
                .doOnNext(command -> player.stopTrack())
                .then();
    }

    @Override
    public Mono<Void> setVolume(MessageCreateEvent event) {
        return Mono.justOrEmpty(event.getMessage().getContent())
                .map(c->Arrays.asList(c.split(" ")))
                //.doOnNext(command -> player.setVolume(Integer.parseInt(command.get(1))))
                .doOnNext(command -> player.setVolume(50))
                .then();
    }

    @Override
    public Mono<Void> pause(MessageCreateEvent event) {
        return Mono.justOrEmpty(event.getMessage().getContent())
                .doOnNext(command -> player.setPaused(true))
                .then();
    }

    @Override
    public Mono<Void> resume(MessageCreateEvent event) {
        return Mono.justOrEmpty(event.getMessage().getContent())
                .doOnNext(command -> player.setPaused(false))
                .then();
    }

    @Override
    public Class getEventType() {
        return MessageCreateEvent.class;
    }

    @Override
    public Mono<String> playerError(Throwable error) {
        return Player.super.playerError(error);
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
