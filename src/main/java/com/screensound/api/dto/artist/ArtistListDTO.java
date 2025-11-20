package com.screensound.api.dto.artist;

import com.screensound.api.entity.Artist;
import com.screensound.api.entity.ArtistType;
import com.screensound.api.entity.Genre;

public record ArtistListDTO(
        String name,
        Genre genre,
        ArtistType artistType
) {
    public ArtistListDTO(Artist artist) {
        this(artist.getName(), artist.getGenre(), artist.getArtistType());
    }
}
