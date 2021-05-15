package ru.pepega;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public class AudioLoadHandler implements AudioLoadResultHandler {

    private AudioPlayer player;

    public AudioLoadHandler(AudioPlayer player) {
        this.player = player;
    }


    @Override
    public void trackLoaded(AudioTrack audioTrack) {
        player.playTrack(audioTrack);
    }

    @Override
    public void playlistLoaded(AudioPlaylist audioPlaylist) {
        TrackScheduler trackScheduler = new TrackScheduler(player);
        for (AudioTrack track : audioPlaylist.getTracks()) {
            trackScheduler.setQueue(track);
        }
    }

    @Override
    public void noMatches() {
        System.out.println("LavaPlayer did not find any audio to extract");
    }

    @Override
    public void loadFailed(FriendlyException e) {
        System.out.println(e);
    }
}
