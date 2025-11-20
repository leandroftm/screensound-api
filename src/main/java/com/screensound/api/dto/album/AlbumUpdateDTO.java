package com.screensound.api.dto.album;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AlbumUpdateDTO(
        @NotNull
        String title,
        Long artistId,
        LocalDate releaseDate
) {
}
