package com.screensound.api.dto.music;

import com.screensound.api.entity.Album;
import com.screensound.api.entity.Artist;
import jakarta.validation.constraints.NotNull;

import java.time.Duration;

public record MusicUpdateDTO(
        String title,
        Artist artist,
        Album album,
        Duration length
) {
}
