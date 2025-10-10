package com.screensound.api.dto.album;

import com.screensound.api.entity.Artist;

import java.time.LocalDate;

public record AlbumUpdateDTO(
        String title,
        Artist artist,
        LocalDate releaseDate
) {
}
