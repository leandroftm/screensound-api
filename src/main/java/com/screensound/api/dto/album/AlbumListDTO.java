package com.screensound.api.dto.album;

import com.screensound.api.entity.Album;

import java.time.LocalDate;

public record AlbumListDTO(
        String title,
        String artistName,
        LocalDate releaseDate
) {
    public AlbumListDTO(Album album) {
        this(album.getTitle(), album.getArtist().getName(), album.getReleaseDate());
    }
}

