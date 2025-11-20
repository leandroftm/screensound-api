package com.screensound.api.dto.artist;

import com.screensound.api.entity.ArtistType;
import com.screensound.api.entity.Genre;
import jakarta.validation.constraints.NotNull;

public record ArtistUpdateDTO(
        @NotNull
        String name,
        Genre genre,
        ArtistType artistType
) {


}
