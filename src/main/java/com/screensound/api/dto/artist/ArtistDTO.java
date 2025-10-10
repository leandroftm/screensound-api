package com.screensound.api.dto.artist;

import com.screensound.api.entity.ArtistType;
import com.screensound.api.entity.Genre;
import jakarta.validation.constraints.NotNull;

public record ArtistDTO(
        @NotNull Long id,
        @NotNull String name,
        @NotNull Genre genre,
        @NotNull ArtistType artistType) {
}
