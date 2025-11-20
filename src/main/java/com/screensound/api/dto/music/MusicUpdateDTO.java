package com.screensound.api.dto.music;

import jakarta.validation.constraints.NotNull;

import java.time.Duration;

public record MusicUpdateDTO(
        @NotNull
        String title,
        Long artistId,
        Long albumId,
        Duration length
) {
}
