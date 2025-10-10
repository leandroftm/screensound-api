package com.screensound.api.dto.album;

import jakarta.validation.constraints.NotNull;

public record AlbumDTO(
        @NotNull
        Long id,
        @NotNull
        String title
) {
}
