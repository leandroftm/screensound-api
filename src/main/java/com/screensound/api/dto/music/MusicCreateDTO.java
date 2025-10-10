package com.screensound.api.dto.music;



import com.screensound.api.entity.Album;
import com.screensound.api.entity.Artist;

import java.time.Duration;

public record MusicCreateDTO(
        String title,
        Duration length
) {
}
