package com.screensound.api.dto.music;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Duration;

public record MusicCreateDTO(
        @NotBlank
        String title,
        @NotNull
        Long artistId,
        @NotNull
        Long albumId,
        @NotNull
        Duration length
) {
}
