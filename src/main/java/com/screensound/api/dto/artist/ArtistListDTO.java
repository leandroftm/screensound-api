package com.screensound.api.dto.artist;

import com.screensound.api.entity.Artist;
import com.screensound.api.entity.ArtistType;
import com.screensound.api.entity.Genre;
import jakarta.validation.constraints.NotNull;

public record ArtistListDTO(
       @NotNull String name,
        Genre genre,
        @NotNull ArtistType artistType) {

    public ArtistListDTO(Artist artist) {
        this(artist.getName(), artist.getGenre(), artist.getArtistType());
    }
}
