package com.screensound.api.dto.artist;

import com.screensound.api.entity.ArtistType;
import com.screensound.api.entity.Genre;

public record ArtistUpdateDTO(
        String name,
        Genre genre,
        ArtistType artistType
) {


}
