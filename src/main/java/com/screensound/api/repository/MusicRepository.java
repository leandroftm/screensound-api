package com.screensound.api.repository;

import com.screensound.api.entity.Album;
import com.screensound.api.entity.Artist;
import com.screensound.api.entity.Music;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MusicRepository extends JpaRepository<Music, Long> {
    Page<Music> findAllByArtist(Artist artist, Pageable pageable);

    Page<Music> findAllByArtistAndAlbum(Artist artist, Album album, Pageable pageable);

    boolean existsByTitleIgnoreCaseAndArtistAndIdNot(String title, Artist artist, Long id);
}
