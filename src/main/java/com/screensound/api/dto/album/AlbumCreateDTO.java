package com.screensound.api.dto.album;



import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AlbumCreateDTO(
        @NotNull
        String title,
        @NotNull
        LocalDate releaseDate
) {
}
