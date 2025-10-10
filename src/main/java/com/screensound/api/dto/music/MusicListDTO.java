package com.screensound.api.dto.music;

import com.screensound.api.entity.Music;

import java.time.Duration;

public record MusicListDTO(
        String title,
        String artistName,
        String albumName,
        Duration length
        ) {
    public MusicListDTO(Music music){
        this(music.getTitle(), music.getArtist().getName(), music.getAlbum().getTitle(), music.getLength());
    }
}
