package com.screensound.api.entity;

import com.screensound.api.dto.music.MusicUpdateDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Duration;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"artist", "album"})
@Table(name = "musics")
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Artist artist;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Album album;

    @NotNull
    private Duration length;

    public Music(String title, Artist artist, Album album, Duration length) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.length = length;
    }

    public void update(@Valid MusicUpdateDTO dto) {
        this.title = dto.title();
        if (dto.artist() != null)
            this.artist = dto.artist();
        if (dto.album() != null)
            this.album = dto.album();
        this.length = dto.length();
    }
}
