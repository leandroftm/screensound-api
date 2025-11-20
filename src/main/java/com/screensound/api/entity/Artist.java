package com.screensound.api.entity;


import com.screensound.api.dto.artist.ArtistCreateDTO;
import com.screensound.api.dto.artist.ArtistUpdateDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"albums", "musics"})
@Table(name = "artists")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Album> albums = new ArrayList<>();

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Music> musics = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ArtistType artistType;

    public Artist(String name, Genre genre, ArtistType artistType) {
        this.name = name;
        this.genre = genre;
        this.artistType = artistType;
    }

    public void setMusics(List<Music> musics) {
        this.musics.clear();
        if (musics != null) {
            musics.forEach(this::addMusic);
        }
    }

    public void addMusic(Music music) {
        music.setArtist(this);
        this.musics.add(music);
    }

    public void setAlbums(List<Album> albums) {
        this.albums.clear();
        if (albums != null) {
            albums.forEach(this::addAlbum);
        }
    }

    public void addAlbum(Album album) {
        album.setArtist(this);
        this.albums.add(album);
    }

    public void update(ArtistUpdateDTO data) {
        this.name = data.name();
        if (data.genre() != null)
            this.genre = data.genre();
        this.artistType = data.artistType();
    }
}
