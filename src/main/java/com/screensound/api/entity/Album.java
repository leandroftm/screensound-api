package com.screensound.api.entity;

import com.screensound.api.dto.album.AlbumUpdateDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"artist", "musics"})
@Table(name = "albums")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @NotNull
    @Column(nullable = false)
    private String title;
    @ManyToOne
    private Artist artist;
    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Music> musics = new ArrayList<>();
    @NotNull
    private LocalDate releaseDate;

    public Album(String title, Artist artist, LocalDate releaseDate) {
        this.title = title;
        this.artist = artist;
        this.releaseDate = releaseDate;
    }

    public void setMusics(List<Music> musics) {
        this.musics.clear();
        if (musics != null) {
            musics.forEach(this::addMusic);
        }
    }

    public void addMusic(Music music) {
        music.setAlbum(this);
        musics.add(music);
    }

    public void update(AlbumUpdateDTO dto) {
        this.title = dto.title();
        if (dto.artist() != null)
            this.artist = dto.artist();
        this.releaseDate = dto.releaseDate();
    }
}
